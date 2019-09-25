package com.app.scilicet.movble;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.protobuf.StringValue;

import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaUsuarioAdapter extends FirestoreRecyclerAdapter <PojoListaUsuario, ListaUsuarioAdapter.ListaUsuarioHolder>{

    public ListaUsuarioAdapter(@NonNull FirestoreRecyclerOptions<PojoListaUsuario> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListaUsuarioHolder listaUsuarioHolder, int i, @NonNull PojoListaUsuario pojoListaUsuario) {
        listaUsuarioHolder.textViewNombres.setText(pojoListaUsuario.getNombres());
        listaUsuarioHolder.textViewApellido.setText(pojoListaUsuario.getApellido());
        listaUsuarioHolder.textViewLocalidad.setText(pojoListaUsuario.getLocalidad());
        listaUsuarioHolder.textViewEstrellas.setText(String.valueOf(pojoListaUsuario.getEstrellas()));

    }

    @NonNull
    @Override
    public ListaUsuarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_lista_usuarios,parent,false);

        return new ListaUsuarioHolder(view);
    }

    class ListaUsuarioHolder extends RecyclerView.ViewHolder {
        TextView textViewLocalidad;
        TextView textViewNombres;
        TextView textViewApellido;
        TextView textViewEstrellas;



        public ListaUsuarioHolder (View itemView){
            super(itemView);
            textViewLocalidad = itemView.findViewById(R.id.id_localidad);
            textViewNombres = itemView.findViewById(R.id.id_nombres);
            textViewApellido = itemView.findViewById(R.id.id_apellido);
            textViewEstrellas = itemView.findViewById(R.id.id_estrellas);



        }
    }


}