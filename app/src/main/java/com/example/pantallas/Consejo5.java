package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Consejo5 extends AppCompatActivity {

    ImageView btn_backInicioConsejos5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo5);

        btn_backInicioConsejos5 = findViewById(R.id.btn_backInicioConsejos5);
        btn_backInicioConsejos5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}