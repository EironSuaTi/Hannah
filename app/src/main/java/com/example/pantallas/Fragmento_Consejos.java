package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Fragmento_Consejos extends Fragment {

    View vista;
    ImageButton consejo1;
    ImageButton consejo2;
    ImageButton consejo3;
    ImageButton consejo4;
    ImageButton consejo5;
    ImageButton consejo6;
    ImageButton consejo7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragmento__consejos, container, false);

        consejo1 = vista.findViewById(R.id.consejo1);
        consejo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo1.class));
            }
        });

        consejo2 = vista.findViewById(R.id.consejo2);
        consejo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo2.class));
            }
        });

        consejo3 = vista.findViewById(R.id.consejo3);
        consejo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo3.class));
            }
        });

        consejo4 = vista.findViewById(R.id.consejo4);
        consejo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo4.class));
            }
        });

        consejo5 = vista.findViewById(R.id.consejo5);
        consejo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo5.class));
            }
        });

        consejo6 = vista.findViewById(R.id.consejo6);
        consejo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo6.class));
            }
        });

        consejo7 = vista.findViewById(R.id.consejo7);
        consejo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Consejo7.class));
            }
        });

        return vista;
    }
}