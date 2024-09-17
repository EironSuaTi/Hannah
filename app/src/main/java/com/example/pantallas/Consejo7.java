package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Consejo7 extends AppCompatActivity {

    ImageView btn_backInicioConsejos7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo7);

        btn_backInicioConsejos7 = findViewById(R.id.btn_backInicioConsejos7);
        btn_backInicioConsejos7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}