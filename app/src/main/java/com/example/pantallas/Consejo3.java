package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Consejo3 extends AppCompatActivity {

    ImageView btn_backInicioConsejos3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo3);

        btn_backInicioConsejos3 = findViewById(R.id.btn_backInicioConsejos3);
        btn_backInicioConsejos3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}