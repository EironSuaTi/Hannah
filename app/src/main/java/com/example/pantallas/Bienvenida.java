package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Bienvenida extends AppCompatActivity {

    TextView inicioSesion;
    Button btn_registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        btn_registrate = findViewById(R.id.btn_registrate);
        btn_registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bienvenida.this, Registro.class);
                startActivity(intent);
            }
        });

        inicioSesion = findViewById(R.id.inicioSesion);
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bienvenida.this, InicioSesion.class);
                startActivity(intent);
            }
        });



    }


}