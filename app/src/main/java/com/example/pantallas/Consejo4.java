package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Consejo4 extends AppCompatActivity {

    ImageView btn_backInicioConsejos4;

    TextView txtLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejo4);

        btn_backInicioConsejos4 = findViewById(R.id.btn_backInicioConsejos4);
        btn_backInicioConsejos4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtLink = findViewById(R.id.txtLink);
        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.20minutos.es/salud/motivos-regla-puede-retrasarse-irregular-4797332/";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

    }
}