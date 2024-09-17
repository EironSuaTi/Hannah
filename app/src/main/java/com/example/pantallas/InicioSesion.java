package com.example.pantallas;

import static com.google.firebase.FirebaseError.ERROR_WRONG_PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class InicioSesion extends AppCompatActivity {

    EditText editEmail;

    EditText editPsw;
    ImageButton PswEye;
    private boolean isPasswordVisible = false; // variable para rastrear si la contraseña es visible o no

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CollectionReference collectionRef;

    ImageView btn_backBienvenidaI;

    Button btn_inicioSesion;

    TextView contraOlvidada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        editEmail = findViewById(R.id.editEmail);
        editPsw = findViewById(R.id.editPsw);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        collectionRef = db.collection("cicloMenstrual");

        /*
            VOLVER A BIENVENIDA
         */

        btn_backBienvenidaI = findViewById(R.id.btn_backBienvenidaI);
        btn_backBienvenidaI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioSesion.this, Bienvenida.class));
            }
        });


        /*
            PARA CAMBIAR LA VISUALIZACION DEL CAMPO PARA LA CONTRASEÑA
         */

        editPsw = findViewById(R.id.editPsw);
        PswEye = findViewById(R.id.PswEye);

        // establece la transformación de contraseña en el EditText
        editPsw.setTransformationMethod(new PasswordTransformationMethod());

        // maneja el clic del botón para cambiar la visibilidad de la contraseña
        PswEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // si la contraseña es visible, cambia la transformación de la contraseña a oculta
                    editPsw.setTransformationMethod(new PasswordTransformationMethod());
                    PswEye.setImageResource(R.drawable.ic_psw_eye_off);
                } else {
                    // si la contraseña está oculta, cambia la transformación de la contraseña a visible
                    editPsw.setTransformationMethod(null);
                    PswEye.setImageResource(R.drawable.ic_psw_eye_on);
                }
                isPasswordVisible = !isPasswordVisible; // cambia el valor de la variable booleana
            }
        });

        /*
            BOTON INICIO SESION (verificacion de campos)
         */
        editEmail = findViewById(R.id.editEmail);
        btn_inicioSesion = findViewById(R.id.btn_inicioSesion);
        btn_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editEmail.getText().toString().trim();
                String psw = editPsw.getText().toString().trim();

                if(validar()){
                    inicioSesion(email, psw);
                }

            }

        });

        /*
            BOTON CONTRASEÑA OLVIDAD
         */

        contraOlvidada = findViewById(R.id.contraOlvidada);
        contraOlvidada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InicioSesion.this, RestablecePsw.class));

            }
        });

    }

    private boolean validar(){

        Boolean retorno = true;

        String correo = editEmail.getText().toString();
        String psw = editPsw.getText().toString();

        if(correo.isEmpty()){
            editEmail.setError("Ingrese su correo por favor");
            retorno = false;
        }
        if(psw.isEmpty()){
            editPsw.setError("Ingrese su contraseña por favor");
            retorno = false;
        }

        return retorno;

    }


    /*
        METODO DE VERIFICACION DE CAMPOS
     */
    public void inicioSesion(String email, String psw) {

        // METODO QUE VERIFICA EL CORREO EN FIREAUTH
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {

                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();

                            // VERIFICACIONES DE CORREO
                            if (signInMethods != null && !signInMethods.isEmpty()) {
                                // El correo electrónico está registrado en Firebase Authentication
                                System.out.println("EL CORREO EXISTE");

                                // INICIA LA SESION
                                mAuth.signInWithEmailAndPassword(email, psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(InicioSesion.this, "Sesion Iniciada con exito", Toast.LENGTH_SHORT).show();

                                            // VALIDACION PREGUNTAS
                                            validacionPreguntas();

                                        } else {
                                            Toast.makeText(InicioSesion.this, "FALLO DE INICIO DE SESION", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(InicioSesion.this, "INICIO DE SESION FALLIDO", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(InicioSesion.this, InicioSesion.class));
                                    }
                                });

                            } else {
                                // El correo electrónico no está registrado en Firebase Authentication
                                Toast.makeText(InicioSesion.this, "EL CORREO NO EXISTE", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error al verificar el correo electrónico
                            Toast.makeText(InicioSesion.this, "CONTACTE CON EL SERVICIO DEL LA APP", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    // metodo que registra si existe una coleccion
    public void validacionPreguntas(){

        if(mAuth.getCurrentUser() != null){

            db.collection("cicloMenstrual").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    if (queryDocumentSnapshots.isEmpty()){      // SI LA COLECCION NO EXISTE
                        System.out.println("La coleccion no existe");
                        startActivity(new Intent(InicioSesion.this, DuracionCiclo.class));
                    } else {
                        System.out.println("La coleccion existe");

                        collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {

                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    if (document.contains("duracionCiclo") && document.contains("duracionPeriodo") && document.contains("diaComienzo")) {
                                        if (document.get("duracionCiclo") != null && document.get("duracionPeriodo") != null && document.get("diaComienzo") != null) {
                                            System.out.println("DATOS RELLENOS");
                                            startActivity(new Intent(InicioSesion.this, Navigation.class));
                                        } else {
                                            System.out.println("DATOS VACIOS");
                                            startActivity(new Intent(InicioSesion.this, DuracionCiclo.class));
                                        }
                                    } else {
                                        System.out.println("CONTIENE LOS CAMPOS");
                                    }
                                }

                            }
                        });

                    }
                }
            });

        }

    }

}
