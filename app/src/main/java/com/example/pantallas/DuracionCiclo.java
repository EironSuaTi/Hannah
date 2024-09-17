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

public class DuracionCiclo extends AppCompatActivity {

    EditText numCiclo;
    ImageView img_sumarCiclo;
    ImageView img_restarCiclo;

    Button btn_siguienteCiclo;
    Button btn_nsncCiclo;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duracion_ciclo);

        // se apunta a la bd
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        numCiclo = findViewById(R.id.numCiclo);

        /*
                BOTON QUE SUMA AL NUMERO
         */
        img_sumarCiclo = findViewById(R.id.img_sumarCiclo);
        img_sumarCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int intNumCiclo = Integer.parseInt(numCiclo.getText().toString());

                if (intNumCiclo == 50){
                    intNumCiclo = 50;
                    String stringNumCiclo= intNumCiclo+"";
                    numCiclo.setText(stringNumCiclo);

                }else{
                    intNumCiclo = intNumCiclo + 1;
                    String stringNumCiclo= intNumCiclo+"";
                    numCiclo.setText(stringNumCiclo);
                }
            }

        });

        /*
                BOTON QUE RESTA AL NUMERO
         */
        img_restarCiclo = findViewById(R.id.img_restarCiclo);
        img_restarCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int intNumCiclo = Integer.parseInt(numCiclo.getText().toString());

                if (intNumCiclo == 1){
                    intNumCiclo = 1;
                    String stringNumCiclo= intNumCiclo+"";
                    numCiclo.setText(stringNumCiclo);

                }else{
                    intNumCiclo = intNumCiclo - 1;
                    String stringNumCiclo= intNumCiclo+"";
                    numCiclo.setText(stringNumCiclo);
                }
            }
        });

        /*
            BOTON QUE INSERTA EN BD EL DATO ELEGIDO Y PASA DE PANTALLA
         */
        btn_siguienteCiclo = findViewById(R.id.btn_siguienteCiclo);
        btn_siguienteCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idUser = mAuth.getCurrentUser().getUid();

                if(idUser != null){

                    // CODIGO DE INSERTE EN BASE DE DATOS

                    Map<String, Object> cicloMens = new HashMap<>();
                    cicloMens.put("duracionCiclo", numCiclo.getText().toString());

                    db.collection("cicloMenstrual").document(idUser).update(cicloMens);
                    System.out.println("INSERTADO");

                    startActivity(new Intent(DuracionCiclo.this, DuracionPeriodo.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);

                } else {
                    Toast.makeText(DuracionCiclo.this, "CONTACTE CON EL SERVICIO DE LA APP", Toast.LENGTH_SHORT).show();
                }

            }

        });

        /*
            BOTON QUE INSERTA EN BD EL DATO PREDETERMINADO Y PASA DE PANTALLA
         */

        btn_nsncCiclo = findViewById(R.id.btn_nsncCiclo);
        btn_nsncCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = mAuth.getCurrentUser().getUid();

                if(userID != null){

                    // si la persona no sabe, numero por defecto (28)
                    int intNumCiclo = Integer.parseInt(numCiclo.getText().toString());

                    intNumCiclo = 28;
                    String stringNumCiclo= intNumCiclo+"";
                    numCiclo.setText(stringNumCiclo);

                    // MISMO CODIGO PARA INSERTAR EN BASE DE DATOS

                    Map<String, Object> cicloMens = new HashMap<>();
                    cicloMens.put("duracionCiclo", numCiclo.getText().toString());
                    cicloMens.put("idUser", userID);

                    db.collection("cicloMenstrual").document(userID).set(cicloMens);
                    System.out.println("INSERTADO");

                    startActivity(new Intent(DuracionCiclo.this, DuracionPeriodo.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);

                } else {
                    Toast.makeText(DuracionCiclo.this, "CONTACTE CON EL SERVICIO DE LA APP", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}