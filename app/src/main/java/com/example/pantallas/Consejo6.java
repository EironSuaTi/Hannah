package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Consejo6 extends AppCompatActivity {

    ImageView btn_backInicioConsejos6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo6);

        btn_backInicioConsejos6 = findViewById(R.id.btn_backInicioConsejos6);
        btn_backInicioConsejos6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}