package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CambiarMisDatos extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    ImageView btn_backPerfilDatos;

    EditText editCambiaNomb;
    EditText editCambiaFech;

    Button btn_guardaCambiosDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_mis_datos);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        editCambiaNomb = findViewById(R.id.editCambiaNomb);


        setUp();

        btn_backPerfilDatos = findViewById(R.id.btn_backPerfilDatos);
        btn_backPerfilDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CambiarMisDatos.this, Perfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });

        editCambiaFech = findViewById(R.id.editCambiaFech);
        editCambiaFech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFecha();
            }
        });

        btn_guardaCambiosDatos = findViewById(R.id.btn_guardaCambiosDatos);
        btn_guardaCambiosDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertaEnUser();

                setUp();

                Toast.makeText(CambiarMisDatos.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                
                startActivity(new Intent(CambiarMisDatos.this, Perfil.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);

            }
        });




    }

    private void setUp() {

        recogerDatosNombre();
        recogerDatosFecha();

    }

    private void recogerDatosNombre(){

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefNomb = db.collection("usuarios").document(idUser);

        docRefNomb.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String nombre = documentSnapshot.getString("nombre");
                    editCambiaNomb.setText(nombre);

                } else {
                    Toast.makeText(CambiarMisDatos.this, "El documento no existe Contactar con servicio de la app", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void recogerDatosFecha(){

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefCiclo = db.collection("usuarios").document(idUser);

        docRefCiclo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String fecha = documentSnapshot.getString("anyoNac");
                    editCambiaFech.setText(fecha);

                } else {
                    Toast.makeText(CambiarMisDatos.this, "El documento no existe Contactar con servicio de la app", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void insertaEnUser() {

        String userID = mAuth.getCurrentUser().getUid();

        Map<String, Object> users = new HashMap<>();
        users.put("nombre", editCambiaNomb.getText().toString());

        db.collection("usuarios").document(userID).update(users);

        Map<String, Object> fecha = new HashMap<>();
        fecha.put("anyoNac", editCambiaFech.getText().toString());

        db.collection("usuarios").document(userID).update(fecha);

    }

    public void guardarFecha() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CambiarMisDatos.this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        editCambiaFech.setText(formattedDate);
                    }
                }, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Establece la fecha máxima como hoy

        datePickerDialog.show();

    }



}