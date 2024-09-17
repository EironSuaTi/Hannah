package com.example.pantallas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText editNombreRegs;
    EditText editAnyoNacRegs;
    EditText editEmailRegs;

    EditText editPswRegs;
    ImageButton pswEyeRegs1;
    private boolean isPasswordVisible1 = false; // variable para rastrear si la contraseña es visible o no

    EditText editPsw2Regs;
    ImageButton pswEyeRegs2;
    private boolean isPasswordVisible2 = false; // variable para rastrear si la contraseña es visible o no


    FirebaseFirestore db;
    FirebaseAuth mAuth;

    ImageView btn_backBienvenidaR;

    Button btn_registro;

    TextView txtPolitica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editNombreRegs = findViewById(R.id.editNombreRegs);
        editAnyoNacRegs = findViewById(R.id.editAnyoNacRegs);
        editEmailRegs = findViewById(R.id.editEmailRegs);

        editPswRegs = findViewById(R.id.editPswRegs);
        pswEyeRegs1 = findViewById(R.id.PswEyeRegs1);

        // se apunta a la bd
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        /*
            VOLVER A BIENVENIDA
         */

        btn_backBienvenidaR = findViewById(R.id.btn_backBienvenidaR);
        btn_backBienvenidaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Bienvenida.class));
            }
        });


        /*
            PARA CAMBIAR LA VISUALIZACION DEL PRIMER CAMPO PARA LA CONTRASEÑA
         */

        // establece la transformación de contraseña en el EditText
        editPswRegs.setTransformationMethod(new PasswordTransformationMethod());

        // maneja el clic del botón para cambiar la visibilidad de la contraseña
        pswEyeRegs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible1) {
                    // si la contraseña es visible, cambia la transformación de la contraseña a oculta
                    editPswRegs.setTransformationMethod(new PasswordTransformationMethod());
                    pswEyeRegs1.setImageResource(R.drawable.ic_psw_eye_off);
                } else {
                    // si la contraseña está oculta, cambia la transformación de la contraseña a visible
                    editPswRegs.setTransformationMethod(null);
                    pswEyeRegs1.setImageResource(R.drawable.ic_psw_eye_on);
                }
                isPasswordVisible1 = !isPasswordVisible1; // cambia el valor de la variable booleana
            }
        });

        /*
            PARA CAMBIAR LA VISUALIZACION DEL SEGUNDO CAMPO PARA LA CONTRASEÑA
         */

        editPsw2Regs = findViewById(R.id.editPsw2Regs);
        pswEyeRegs2 = findViewById(R.id.PswEyeRegs2);

        // establece la transformación de contraseña en el EditText
        editPsw2Regs.setTransformationMethod(new PasswordTransformationMethod());

        // maneja el clic del botón para cambiar la visibilidad de la contraseña
        pswEyeRegs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible2) {
                    // si la contraseña es visible, cambia la transformación de la contraseña a oculta
                    editPsw2Regs.setTransformationMethod(new PasswordTransformationMethod());
                    pswEyeRegs2.setImageResource(R.drawable.ic_psw_eye_off);
                } else {
                    // si la contraseña está oculta, cambia la transformación de la contraseña a visible
                    editPsw2Regs.setTransformationMethod(null);
                    pswEyeRegs2.setImageResource(R.drawable.ic_psw_eye_on);
                }
                isPasswordVisible2 = !isPasswordVisible2; // cambia el valor de la variable booleana
            }
        });

        /*
           PONER LA FECHA DE NACIMIENTO EN EL EDITTEXT CON UN CALENDARIO
         */

        editAnyoNacRegs = findViewById(R.id.editAnyoNacRegs);
        editAnyoNacRegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Registro.this, R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                                editAnyoNacRegs.setText(formattedDate);
                            }
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Establece la fecha máxima como hoy

                datePickerDialog.show();

            }
        });

        /*
            REGISTRARSE
         */
        btn_registro = findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String psw = editPswRegs.getText().toString().trim();
                String psw2 = editPsw2Regs.getText().toString().trim();

                registrar(psw, psw2);

            }
        });

        txtPolitica = findViewById(R.id.txtPolitica);
        txtPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://hannahpolitica.weebly.com/poliacutetica-de-privacidad.html";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

}

    private boolean validar(){

        Boolean retorno = true;

        String nombre = editNombreRegs.getText().toString().trim();
        String anyo = editAnyoNacRegs.getText().toString().trim();
        String email = editEmailRegs.getText().toString().trim();
        String psw = editPswRegs.getText().toString().trim();
        String psw2 = editPsw2Regs.getText().toString().trim();

        if(nombre.isEmpty()){
            editNombreRegs.setError("Ingrese su nombre");
            retorno = false;
        }
        if(anyo.isEmpty()){
            editAnyoNacRegs.setError("Ingrese su año de nacimiento");
            retorno = false;
        }
        if(email.isEmpty()){
            editEmailRegs.setError("Ingrese su correo");
            retorno = false;
        }
        if(psw.isEmpty()){
            editPswRegs.setError("Ingrese su contraseña");
            retorno = false;
        }
        if(psw2.isEmpty()){
            editPsw2Regs.setError("Ingrese su confirmacion de contraseña");
            retorno = false;
        }

        return retorno;

    }

    // metodo que registra en base de datos
    public void registrar(String psw, String psw2) {

        if(validar()) { // si all esta completo

            // hacemos comprobaciones de la base de datos
            // si los datos no existen en esta, se añaden, sino, se avisa al usuario

            // comprobamos que la contraseña tenga un minimo de 8 caracteres
            if (psw.length() >= 8){

                // comprobamos que las contraseñas coinciden
                if (psw.equals(psw2)){

                    System.out.println("Contraseña coincide");
                    // si coincide insertariamos en bd

                    mAuth.createUserWithEmailAndPassword(editEmailRegs.getText().toString(), editPswRegs.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                System.out.println("REGISTRADO");

                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("nombre", editNombreRegs.getText().toString().trim());
                                usuario.put("anyoNac", editAnyoNacRegs.getText().toString().trim());
                                usuario.put("email", editEmailRegs.getText().toString().trim());
                                usuario.put("psw", editPswRegs.getText().toString().trim());
                                usuario.put("photo", "");

                                String userID = mAuth.getCurrentUser().getUid();

                                // se introduce en base de datos
                                db.collection("usuarios").document(userID).set(usuario);

                                System.out.println("INSERTADO");

                                // si el usuario existe, pasamos de pantalla
                                if(userID != null){
                                    startActivity(new Intent(Registro.this, DuracionCiclo.class));

                                    String idUser = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> tablas = new HashMap<>();

                                    db.collection("cicloMenstrual").document(idUser).set(tablas);

                                    // se crean las tablas de los sintomas

                                    String bloq1 = "1_actividadSex";
                                    String bloq2 = "2_estadoAnimo";
                                    String bloq3 = "3_sintomas";
                                    String bloq4 = "4_flujVag";

                                    // CREACION DE TABLAS

                                    db.collection(bloq1).document(idUser).set(tablas);
                                    db.collection(bloq2).document(idUser).set(tablas);
                                    db.collection(bloq3).document(idUser).set(tablas);
                                    db.collection(bloq4).document(idUser).set(tablas);

                                    Map<String, Object> fecha = new HashMap<>();
                                    fecha.put("fecha", "");

                                    db.collection(bloq1).document(idUser).set(fecha);
                                    db.collection(bloq2).document(idUser).set(fecha);
                                    db.collection(bloq3).document(idUser).set(fecha);
                                    db.collection(bloq4).document(idUser).set(fecha);



                                } else {
                                    Toast.makeText(Registro.this, "CONTACTE CON EL SERVICIO DE LA APP", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(Registro.this, "Este correo ya esta en uso", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "8 CARACTERES COMO MINIMO EN LA CONTRASEÑA", Toast.LENGTH_SHORT).show();
            }

        }

    }

}
