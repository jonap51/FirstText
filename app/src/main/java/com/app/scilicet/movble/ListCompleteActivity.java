package com.app.scilicet.movble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;


public class ListCompleteActivity extends AppCompatActivity {

    TextView tituloLista;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usuarioRef = db.collection("aire acondicionado").document("argentina").collection("buenos aires").document("moreno").collection("users");

    private ListaUsuarioAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_complete);

        tituloLista = findViewById(R.id.titulo_lista);

        //Toolbar con Cambio de Título Automático

        int parametroRecibido = getIntent().getIntExtra("Id", 0);

        Toolbar toolbar = findViewById(R.id.barraHerramientas);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle(parametroRecibido);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blancoAla));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white_24dp);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // RecyclerView
        setUpRecyclerView();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Flecha "Back" te lleva atrás
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpRecyclerView(){

        Query query = usuarioRef.orderBy("localidad", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions <PojoListaUsuario> options = new FirestoreRecyclerOptions.Builder<PojoListaUsuario>()
                .setQuery(query,PojoListaUsuario.class)
                .build();

        adapter = new ListaUsuarioAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}