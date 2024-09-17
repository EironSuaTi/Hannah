package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DiaComienzo extends AppCompatActivity {

    ImageView btn_backDia;

    CalendarView calendario;

    Button btn_nsncDia;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_comienzo);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        calendario = findViewById(R.id.calendario);

        // Establece la fecha máxima permitida (fecha actual)
        long hoyNow = Calendar.getInstance().getTimeInMillis();
        calendario.setMaxDate(hoyNow);

        String userID = mAuth.getCurrentUser().getUid();

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                month ++;

                // si el dato (numero) tiene solo un digito, pone un 0 delante
                String dia = String.format("%02d", dayOfMonth);
                String mes = String.format("%02d", month);
                String anyo = String.valueOf(year);

                String diaComienzo = anyo + "-" + mes + "-" + dia;

                // CODIGO DE INSERTE EN BASE DE DATOS

                Map<String, Object> cicloMens = new HashMap<>();
                cicloMens.put("diaComienzo", diaComienzo);

                db.collection("cicloMenstrual").document(userID).update(cicloMens);
                System.out.println("INSERTADO");

                startActivity(new Intent(DiaComienzo.this, Navigation.class));

            }
        });

        /*
            BOTON QUE INSERTA EN BD EL DATO PREDEFINIDO Y PASA DE PANTALLA
         */

        btn_nsncDia = findViewById(R.id.btn_nsncDia);
        btn_nsncDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diaComienzo = "";

                Map<String, Object> cicloMens = new HashMap<>();
                cicloMens.put("diaComienzo", diaComienzo);

                db.collection("cicloMenstrual").document(userID).update(cicloMens);
                System.out.println("INSERTADO");

                startActivity(new Intent(DiaComienzo.this, Navigation.class));
            }
        });

        /*
            BOTON QUE VUELVE DE PANTALLA
         */

        btn_backDia = findViewById(R.id.btn_backDia);
        btn_backDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiaComienzo.this, DuracionPeriodo.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
            }
        });

    }


}