package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SintomasVista extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    ImageView btn_backPerfilSintVer;

    TextView txtFecha;

    TextView txtSX;
    TextView txtEA;
    TextView txtSin;
    TextView txtFluj;

    String bloq1 = "1_actividadSex";
    String bloq2 = "2_estadoAnimo";
    String bloq3 = "3_sintomas";
    String bloq4 = "4_flujVag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_vista);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        txtFecha = findViewById(R.id.txtFecha);

        txtSX = findViewById(R.id.txtSX);
        txtEA = findViewById(R.id.txtEA);
        txtSin = findViewById(R.id.txtSin);
        txtFluj = findViewById(R.id.txtFluj);


        btn_backPerfilSintVer = findViewById(R.id.btn_backPerfilSintVer);
        btn_backPerfilSintVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SintomasVista.this, Navigation.class));
            }
        });

        setUp();

    }

    private void setUp() {

        System.out.println("----------------- Vista Sintomas -----------------");

        recogeFecha();

        datosBloq(bloq1, txtSX);
        datosBloq(bloq2, txtEA);
        datosBloq(bloq3, txtSin);
        datosBloq(bloq4, txtFluj);

    }

    private void datosBloq(String bloq, TextView txt) {

        String idUser = mAuth.getCurrentUser().getUid();
        DocumentReference bloque = db.collection(bloq).document(idUser);
        ArrayList<String> array = new ArrayList<>();

        bloque.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Obtener lista de campos del documento
                    Map<String, Object> datos = documentSnapshot.getData();
                    Set<String> campos = datos.keySet();

                    // Acceder a los valores de cada campo
                    for (String campo : campos) {
                        if (!campo.equals("fecha")) {
                            Object valor = datos.get(campo);
//                            System.out.println("Campos " + campo + ": " + valor);
                            array.add(valor.toString());
                        }
                    }

                    mostrarEnTextView(array, txt);

                } else {
                    System.out.println("No se encontró un documento con el ID de usuario especificado.");
                }
            }
        });

    }

    private void mostrarEnTextView(ArrayList<String> datos, TextView textView) {
        // Convertir el ArrayList en una cadena
        String cadena = TextUtils.join(", ", datos);

        // Establecer la cadena como texto del TextView
        textView.setText(cadena);
    }

    private void recogeFecha() {


        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference fecha = db.collection(bloq1).document(idUser);

        fecha.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    // Verificar si el documento existe
                    if (documentSnapshot.exists()) {

                        String fecha = documentSnapshot.getString("fecha");

                        if (fecha.equals("")){

                            txtFecha.setText("Registra tus sintomas para poder ver el registro");

                        } else {

                            txtFecha.setText("Tus sintomas del dia " + fecha + " son:");

                        }



                    } else {
                        Toast.makeText(SintomasVista.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }
}