package com.app.scilicet.movble;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroUnoFragment extends Fragment {


    public RegistroUnoFragment() {
        // Required empty public constructor
    }


    Button button_continuar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_registro_uno, container, false);

        button_continuar = rootview.findViewById(R.id.button_continuar);



        return rootview;
    }

}
