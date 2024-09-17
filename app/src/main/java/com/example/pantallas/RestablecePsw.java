package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecePsw extends AppCompatActivity {

    FirebaseAuth mAuth;

    ImageView btn_backInicio;

    TextView editEmailR;
    Button restPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablece_psw);

        mAuth = FirebaseAuth.getInstance();

        btn_backInicio = findViewById(R.id.btn_backInicio);
        btn_backInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestablecePsw.this, InicioSesion.class));
            }
        });

        editEmailR = findViewById(R.id.editEmailR);

        restPsw = findViewById(R.id.restPsw);
        restPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editEmailR.getText().toString().isEmpty()){
                    Toast.makeText(RestablecePsw.this, "Ingrese el correo por favor", Toast.LENGTH_SHORT).show();
                } else {
                    restablecerPsw(editEmailR.getText().toString());
                }
            }
        });

    }

    public void restablecerPsw(String email){

        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RestablecePsw.this, "Correo enviado", Toast.LENGTH_SHORT).show();
            }
        });

    }
}