package com.app.scilicet.movble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditarPerfilActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseFirestore db;

    private RequestQueue mQueue;

    private ArrayList <String> arrayListSp;
    private Spinner sp,sp1,sp2;
    private String selectedProv, selectedDep, selectedLoc;

     ChipGroup chipGroup;

    private TextInputEditText id_setNombre, id_setApellido, id_setNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        sp = findViewById(R.id.spinnerProv);
        sp1= findViewById(R.id.spinnerDep);
        sp2= findViewById(R.id.spinnerLoc);


        id_setNombre = findViewById(R.id.id_setNombre);
        id_setApellido = findViewById(R.id.id_setApellido);
        id_setNumber = findViewById(R.id.id_setNumber);


     




        String url = "https://apis.datos.gob.ar/georef/api/provincias?";
        mQueue = Volley.newRequestQueue(this);
        jsonObjectRequest(sp, url, getString(R.string.provincias));

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedProv = parent.getItemAtPosition(position).toString();
                jsonObjectRequest(sp1, "https://apis.datos.gob.ar/georef/api/departamentos?provincia="+selectedProv+"&campos=id&max=5000", getString(R.string.departamentos));
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDep = parent.getItemAtPosition(position).toString();
                jsonObjectRequest(sp2, "https://apis.datos.gob.ar/georef/api/localidades?departamento="+selectedDep+"&campos=id&max=5000", getString(R.string.localidades));


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedLoc = parent.getItemAtPosition(position).toString();

                Toast.makeText(EditarPerfilActivity.this, "DIO ESTE RESULTADO"+selectedLoc, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });












    }


    private void jsonObjectRequest(final Spinner spinner, String url, final String key){

        arrayListSp = new ArrayList<>();
        arrayListSp.add(" Seleccione");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final JSONArray jsonArraySpinner = response.getJSONArray(key);
                            for (int i = 0; i < jsonArraySpinner.length(); i++) {
                                JSONObject objectProv = jsonArraySpinner.getJSONObject(i);

                                arrayListSp.add(objectProv.getString("nombre"));

                            }

                            Collections.sort(arrayListSp);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(EditarPerfilActivity.this,
                                    android.R.layout.simple_spinner_item, arrayListSp );

                            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            spinner.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.ok_toolbar, menu);
        // MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.menu_opcion_home, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        if (item.getItemId() == R.id.id_ok) {//your code

            db = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {

                        Map<String, Object> city = new HashMap<>();
                        city.put("nombres",  Objects.requireNonNull(id_setNombre.getText()).toString());
                        city.put("apellido", Objects.requireNonNull(id_setApellido.getText()).toString());
                        city.put("telefono", Objects.requireNonNull(id_setNumber.getText()).toString());
                        city.put("email", Objects.requireNonNull(user.getEmail()));
                        city.put("userID", user.getUid());


                        db.collection("_usuarios").document("Argentina").collection(selectedProv)
                                .document(selectedDep).collection(selectedLoc).document(user.getUid()).set(city);


                        db.collection("argentina1620").document("Argentina").collection(selectedProv)
                                .document(selectedDep).collection(selectedLoc).document(user.getUid()).set(city);



                    }
                }
            };

            // EX : call intent if you want to swich to other activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
