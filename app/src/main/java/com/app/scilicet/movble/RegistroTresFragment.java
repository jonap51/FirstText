package com.app.scilicet.movble;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


/**
  *A simple {@link Fragment} subclass.
 */
public class RegistroTresFragment extends Fragment {

    public RegistroTresFragment() {

        // Required empty public constructor
    }

    private RequestQueue mQueue;

    private ArrayList<String>arrayListProv, arrayListLoc, arrayListDep;
    private Spinner sp,sp1,sp2;

    private String selectedItem, selectedItem1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_registro_tres, container, false);

        sp = view.findViewById(R.id.spinner1);
        sp1= view.findViewById(R.id.spinnerDep);
        sp2= view.findViewById(R.id.spinnerLoc);

         mQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        spinnerProvincias();
        // Inflate the layout for this fragment
        return  view;
    }



    private void spinnerProvincias(){
        arrayListProv= new ArrayList<>();
        arrayListProv.add(" seleccione");

        String url = "https://apis.datos.gob.ar/georef/api/provincias?&campos=id&max=30";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final JSONArray jsonArrayProv = response.getJSONArray("provincias");
                            for (int i = 0; i < jsonArrayProv.length(); i++) {
                                JSONObject objectProv = jsonArrayProv.getJSONObject(i);

                                arrayListProv.add(objectProv.getString("nombre"));

                            }

                            Collections.sort(arrayListProv);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                    android.R.layout.simple_spinner_item, arrayListProv);

                            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    selectedItem= parent.getItemAtPosition(position).toString();
                                    spinnerDepartamento();

                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


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


    private void spinnerDepartamento(){

        String url1 = "https://apis.datos.gob.ar/georef/api/departamentos?provincia="+selectedItem+"&campos=id&max=5000";
        arrayListDep= new ArrayList<>();
        arrayListDep.add(" seleccione");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            final JSONArray jsonArrayDep = response.getJSONArray("departamentos");
                            for (int i = 0; i < jsonArrayDep.length(); i++) {
                                JSONObject objectDep = jsonArrayDep.getJSONObject(i);

                                arrayListDep.add(objectDep.getString("nombre"));
                            }

                            Collections.sort(arrayListDep);

                            ArrayAdapter<String>adapterDep = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                    android.R.layout.simple_spinner_item, arrayListDep);

                            adapterDep.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp1.setAdapter(adapterDep);
                            adapterDep.notifyDataSetChanged();


                            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    selectedItem1= parent.getItemAtPosition(position).toString();

                                    spinnerLocalidades();

                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



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


    //https://apis.datos.gob.ar/georef/api/localidades?departamento=22112  Obtengo la localidad
    //https://apis.datos.gob.ar/georef/api/departamentos?provincia=06  Obtengo el Departamento
    //https://apis.datos.gob.ar/georef/api/provincias? Obtengo las Provincias


    private void spinnerLocalidades(){

        String url2 = "https://apis.datos.gob.ar/georef/api/localidades?departamento="+selectedItem1+"&campos=id&max=5000";
        arrayListLoc= new ArrayList<>();
        arrayListLoc.add(" seleccione");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            final JSONArray jsonArrayLoc = response.getJSONArray("localidades");
                            for (int i = 0; i < jsonArrayLoc.length(); i++) {
                                JSONObject objectLoc = jsonArrayLoc.getJSONObject(i);

                                arrayListLoc.add(objectLoc.getString("nombre"));
                            }

                            Collections.sort(arrayListLoc);

                            ArrayAdapter<String>adapterLoc = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                                    android.R.layout.simple_spinner_item, arrayListLoc);

                            adapterLoc.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp2.setAdapter(adapterLoc);
                            adapterLoc.notifyDataSetChanged();

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
}



