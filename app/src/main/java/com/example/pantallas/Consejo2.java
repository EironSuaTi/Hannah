package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Consejo2 extends AppCompatActivity {

    ImageView btn_backInicioConsejos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo2);

        btn_backInicioConsejos2 = findViewById(R.id.btn_backInicioConsejos2);
        btn_backInicioConsejos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}