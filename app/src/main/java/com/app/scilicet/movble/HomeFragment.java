package com.app.scilicet.movble;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    private FloatingActionButton floatingActionButton;
    private  Toolbar toolbar;
    private ImageButton buttonAir, buttonHerr, buttonPintor, buttonServ,
            buttonAlba, buttonCerr, buttonCarp, buttonPlom, buttonJard, buttonElec;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
  //  DocumentReference db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        
        //creación de Toolbar
        toolbar = view.findViewById(R.id.id_toolbarHome);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Objects.requireNonNull(activity).setSupportActionBar(toolbar);
        activity.setTitle("");
        setHasOptionsMenu(true);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

      mGoogleSignInClient= GoogleSignIn.getClient(getActivity(), gso);



       //devuelve la ultima cuenta que esta activa
        //  GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Objects.requireNonNull(getActivity()));




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);

                } else {
                    goLogInScreen();
                }
            }
        };




        //Floating Button Share
        floatingActionButton = view.findViewById(R.id.id_share);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Solicita u ofrece  servicios para el hogar sin comisión");
                startActivity(Intent.createChooser(intent, getString(R.string.compartir_con_amigos)));
            }

        });


        //Tap Buttons Services
        buttonAir = view.findViewById(R.id.img_aire);
        buttonHerr = view.findViewById(R.id.img_herrero);
        buttonPintor = view.findViewById(R.id.img_pintor);
        buttonServ = view.findViewById(R.id.img_service);
        buttonAlba = view.findViewById(R.id.img_alba);
        buttonCerr = view.findViewById(R.id.img_cerra);
        buttonCarp = view.findViewById(R.id.img_carp);
        buttonPlom = view.findViewById(R.id.img_plom);
        buttonJard = view.findViewById(R.id.img_jard);
        buttonElec = view.findViewById(R.id.img_elec);

        //Buttons Services
        tapButton(buttonAir, R.string.aire_acondicionado);
        tapButton(buttonHerr, R.string.herrero_forjado);
        tapButton(buttonPintor, R.string.pintor);
        tapButton(buttonServ, R.string.service_heladera);
        tapButton(buttonAlba, R.string.albanil);
        tapButton(buttonCerr, R.string.cerrajero);
        tapButton(buttonCarp, R.string.carpintero);
        tapButton(buttonPlom, R.string.plomero_fontanero);
        tapButton(buttonJard, R.string.jardinero);
        tapButton(buttonElec, R.string.electricista);

        return view;
    }


    private void tapButton(ImageButton imageButton, final int nameService){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), ListCompleteActivity.class);
                i.putExtra("Id", nameService);
                startActivity(i);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_opcion_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ayuda:

                return true;

            case R.id.salir:
                firebaseAuth.signOut();
                mGoogleSignInClient.signOut();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void goLogInScreen() {
        Intent intent = new Intent (getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();

        if (firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


    private void setUserData(FirebaseUser user) {

        Map<String, Object> city = new HashMap<>();
        city.put("name", Objects.requireNonNull(user.getDisplayName()));
        city.put("email", Objects.requireNonNull(user.getEmail()));
        city.put("userID", Objects.requireNonNull(user.getUid()));


        String user_id = user.getUid();

        db.collection("cities").document(user_id)
                .set(city);





        //nameTextView.setText(user.getDisplayName());
        //emailTextView.setText(user.getEmail());
        //idTextView.setText(user.getUid());
      /*  Glide.with(this)
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(photoImageView);
        idTextView.setText(user.getEmail());*/
    }


}


