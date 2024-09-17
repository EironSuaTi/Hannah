package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DuracionPeriodo extends AppCompatActivity {

    ImageView btn_backPeriodo;

    EditText numPeriodo;
    ImageView img_sumarPeriodo;
    ImageView img_restarPeriodo;

    Button btn_siguientePeriodo;
    Button btn_nsncPeriodo;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duracion_periodo);

        // se apunta a la bd
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        numPeriodo = findViewById(R.id.numPeriodo);

        /*
                BOTON QUE SUMA AL NUMERO
         */
        img_sumarPeriodo = findViewById(R.id.img_sumarPeriodo);
        img_sumarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int intNumPeriodo = Integer.parseInt(numPeriodo.getText().toString());

                if (intNumPeriodo == 10){
                    intNumPeriodo = 10;
                    String stringNumPeriodo= intNumPeriodo+"";
                    numPeriodo.setText(stringNumPeriodo);

                }else{
                    intNumPeriodo = intNumPeriodo + 1;
                    String stringNumPeriodo= intNumPeriodo+"";
                    numPeriodo.setText(stringNumPeriodo);
                }
            }

        });

        /*
                BOTON QUE RESTA AL NUMERO
         */
        img_restarPeriodo = findViewById(R.id.img_restarPeriodo);
        img_restarPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int intNumPeriodo = Integer.parseInt(numPeriodo.getText().toString());

                if (intNumPeriodo == 3){
                    intNumPeriodo = 3;
                    String stringNumPeriodo= intNumPeriodo+"";
                    numPeriodo.setText(stringNumPeriodo);

                }else{
                    intNumPeriodo = intNumPeriodo - 1;
                    String stringNumPeriodo= intNumPeriodo+"";
                    numPeriodo.setText(stringNumPeriodo);
                }
            }

        });

        /*
            BOTON QUE INSERTA EN BD EL DATO ELEGIDO Y PASA DE PANTALLA
         */

        btn_siguientePeriodo = findViewById(R.id.btn_siguientePeriodo);
        btn_siguientePeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idUser = mAuth.getCurrentUser().getUid();

                // CODIGO DE INSERTE EN BASE DE DATOS

                Map<String, Object> cicloMens = new HashMap<>();
                cicloMens.put("duracionPeriodo", numPeriodo.getText().toString());

                db.collection("cicloMenstrual").document(idUser).update(cicloMens);
                System.out.println("INSERTADO");

                if(idUser != null){
                    startActivity(new Intent(DuracionPeriodo.this, DiaComienzo.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                } else {
                    Toast.makeText(DuracionPeriodo.this, "CONTACTE CON EL SERVICIO DE LA APP", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*
            BOTON QUE INSERTA EN BD EL DATO PREDETERMINADO Y PASA DE PANTALLA
         */

        btn_nsncPeriodo = findViewById(R.id.btn_nsncPeriodo);
        btn_nsncPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = mAuth.getCurrentUser().getUid();

                if(userID != null){

                    // si la persona no sabe, numero por defecto (28)
                    int intNumCiclo = Integer.parseInt(numPeriodo.getText().toString());

                    intNumCiclo = 5;
                    String stringNumCiclo= intNumCiclo+"";
                    numPeriodo.setText(stringNumCiclo);

                    // MISMO CODIGO PARA INSERTAR EN BASE DE DATOS

                    Map<String, Object> cicloMens = new HashMap<>();
                    cicloMens.put("duracionPeriodo", numPeriodo.getText().toString());

                    db.collection("cicloMenstrual").document(userID).update(cicloMens);
                    System.out.println("INSERTADO");

                    startActivity(new Intent(DuracionPeriodo.this, DiaComienzo.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);

                } else {
                    Toast.makeText(DuracionPeriodo.this, "CONTACTE CON EL SERVICIO DE LA APP", Toast.LENGTH_SHORT).show();
                }

            }

        });

        /*
            BOTON QUE VUELVE DE PANTALLA
         */

        btn_backPeriodo = findViewById(R.id.btn_backPeriodo);
        btn_backPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DuracionPeriodo.this, DuracionCiclo.class));
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                // primer campo: como quieres que se vaya la inicial
                // segundo campo: como quieres que aparezca la siguiente
            }
        });

    }

}