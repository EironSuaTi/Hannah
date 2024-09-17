package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CambiarMiCiclo extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    ImageView btn_backPerfilCiclo;

    EditText editCiclo;
    EditText editPeriodo;
    EditText editDia;

    CalendarView calendarioCambio;

    Button btn_guardaCambiosCiclo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_mi_ciclo);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editCiclo = findViewById(R.id.editCiclo);
        editDia = findViewById(R.id.editDia);
        editPeriodo = findViewById(R.id.editPeriodo);

        calendarioCambio = findViewById(R.id.calendarioCambio);

        // Establece la fecha máxima permitida (fecha actual)
        long hoyNow = Calendar.getInstance().getTimeInMillis();
        calendarioCambio.setMaxDate(hoyNow);

        calendarioCambio.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                month ++;

                // si el dato (numero) tiene solo un digito, pone un 0 delante
                String dia = String.format("%02d", dayOfMonth);
                String mes = String.format("%02d", month);
                String anyo = String.valueOf(year);

                String diaComienzo = anyo + "-" + mes + "-" + dia;
                editDia.setText(diaComienzo);

            }
        });

        setUp();

        btn_backPerfilCiclo = findViewById(R.id.btn_backPerfilCiclo);
        btn_backPerfilCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CambiarMiCiclo.this, Perfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

        btn_guardaCambiosCiclo = findViewById(R.id.btn_guardaCambiosCiclo);
        btn_guardaCambiosCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertaEnCiclo();

                Toast.makeText(CambiarMiCiclo.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(CambiarMiCiclo.this, Perfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });



    }

    private void setUp() {

        recogerDatosCiclo();
        recogerDatosPeriodo();
        recogerDatosDia();

    }

    private void recogerDatosCiclo() {

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefNomb = db.collection("cicloMenstrual").document(idUser);

        docRefNomb.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String duracionCiclo = documentSnapshot.getString("duracionCiclo");
                    editCiclo.setText(duracionCiclo);

                } else {
                    Toast.makeText(CambiarMiCiclo.this, "El documento no existe Contactar con servicio de la app", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void recogerDatosPeriodo() {

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefNomb = db.collection("cicloMenstrual").document(idUser);

        docRefNomb.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String nombre = documentSnapshot.getString("duracionPeriodo");
                    editPeriodo.setText(nombre);

                } else {
                    Toast.makeText(CambiarMiCiclo.this, "El documento no existe Contactar con servicio de la app", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void recogerDatosDia() {

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefNomb = db.collection("cicloMenstrual").document(idUser);

        docRefNomb.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String dia = documentSnapshot.getString("diaComienzo");

                    if(dia.equals("")){
                        editDia.setText("Seleccione una fecha");
                    } else {
                        editDia.setText(dia);
                    }

                } else {
                    Toast.makeText(CambiarMiCiclo.this, "El documento no existe Contactar con servicio de la app", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void insertaEnCiclo() {

        String userID = mAuth.getCurrentUser().getUid();

        db.collection("cicloMenstrual").document(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        Map<String, Object> ciclo = new HashMap<>();
        ciclo.put("duracionCiclo", editCiclo.getText().toString());

        db.collection("cicloMenstrual").document(userID).set(ciclo);
        System.out.println("Cambiado Ciclo");


        Map<String, Object> periodo = new HashMap<>();
        periodo.put("duracionPeriodo", editPeriodo.getText().toString());

        db.collection("cicloMenstrual").document(userID).update(periodo);
        System.out.println("Cambiado Periodo");


        Map<String, Object> dia = new HashMap<>();
        dia.put("diaComienzo", editDia.getText().toString());

        db.collection("cicloMenstrual").document(userID).update(dia);
        System.out.println("Cambiado Dia");

    }


}