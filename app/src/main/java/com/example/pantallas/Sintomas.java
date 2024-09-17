package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Sintomas extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    TextView fechatxt;

    ImageView btn_backPerfilSint;

    DayScrollDatePicker calendario_datepicker;

    LinearLayout actvSex;
    LinearLayout estAnim;
    LinearLayout sintomas;
    LinearLayout flujVag;

    ToggleButton tog11;
    ToggleButton tog12;
    ToggleButton tog13;
    ToggleButton tog14;

    ToggleButton tog21;
    ToggleButton tog22;
    ToggleButton tog23;
    ToggleButton tog24;
    ToggleButton tog25;
    ToggleButton tog26;
    ToggleButton tog27;

    ToggleButton tog31;
    ToggleButton tog32;
    ToggleButton tog33;
    ToggleButton tog34;
    ToggleButton tog35;
    ToggleButton tog36;
    ToggleButton tog37;
    ToggleButton tog38;

    ToggleButton tog41;
    ToggleButton tog42;
    ToggleButton tog43;
    ToggleButton tog44;

    String fecha;
    Button btn_guardarSintomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        tog11 = findViewById(R.id.tog11);
        tog12 = findViewById(R.id.tog12);
        tog13 = findViewById(R.id.tog13);
        tog14 = findViewById(R.id.tog14);

        tog21 = findViewById(R.id.tog21);
        tog22 = findViewById(R.id.tog22);
        tog23 = findViewById(R.id.tog23);
        tog24 = findViewById(R.id.tog24);
        tog25 = findViewById(R.id.tog25);
        tog26 = findViewById(R.id.tog26);
        tog27 = findViewById(R.id.tog27);

        tog31 = findViewById(R.id.tog31);
        tog32 = findViewById(R.id.tog32);
        tog33 = findViewById(R.id.tog33);
        tog34 = findViewById(R.id.tog34);
        tog35 = findViewById(R.id.tog35);
        tog36 = findViewById(R.id.tog36);
        tog37 = findViewById(R.id.tog37);
        tog38 = findViewById(R.id.tog38);

        tog41 = findViewById(R.id.tog41);
        tog42 = findViewById(R.id.tog42);
        tog43 = findViewById(R.id.tog43);
        tog44 = findViewById(R.id.tog44);


        fechatxt = findViewById(R.id.fechatxt);

        actvSex = findViewById(R.id.actvSex);
        estAnim = findViewById(R.id.estAnim);
        sintomas = findViewById(R.id.sintomas);
        flujVag = findViewById(R.id.flujVag);

        setUp();

        btn_backPerfilSint = findViewById(R.id.btn_backPerfilSint);
        btn_backPerfilSint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sintomas.this, Navigation.class));
            }
        });


        calendario_datepicker = findViewById(R.id.calendario_datepicker);
        calendario_datepicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if(date != null){

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    int dia = calendar.get(Calendar.DATE);
                    int mes = calendar.get(Calendar.MONTH) + 1;
                    int anyo = calendar.get(Calendar.YEAR);

                    fecha = (dia + " / " + mes + " / " + anyo);

                }

            }
        });

        // metodo que señala si el sintoma esta o no señalado
        compruebaCheck();

        btn_guardarSintomas = findViewById(R.id.btn_guardarSintomas);
        btn_guardarSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerYguardar();
            }
        });


    }

    private void setUp() {

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM", Locale.getDefault());//"dd 'de' MMMM 'de' yyyy"
        String formattedDate = dateFormat.format(currentDate);

        fechatxt.setText("Hoy: " + formattedDate);



    }

    private void compruebaCheck() {

        /*
                ACTIVIDAD SEXUAL Y DESEO (PRIMERO)
         */
        //  deseo sexual
        tog11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog11.isChecked()){
                    tog11.setBackgroundResource(R.drawable.icono_sx_deseo_sexual_check);
                } else {
                    tog11.setBackgroundResource(R.drawable.icono_sx_deseo_sexual);
                }

            }
        });

        //  masturbacion
        tog12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog12.isChecked()){
                    tog12.setBackgroundResource(R.drawable.icono_sx_masturbacion_check);
                } else {
                    tog12.setBackgroundResource(R.drawable.icono_sx_masturbacion);
                }

            }
        });

        //  sexo con proteccion
        tog13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog13.isChecked()){
                    tog13.setBackgroundResource(R.drawable.icono_sx_sexo_con_proteccion_check);
                } else {
                    tog13.setBackgroundResource(R.drawable.icono_sx_sexo_con_proteccion);
                }

            }
        });

        //  sexo sin proteccion
        tog14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog14.isChecked()){
                    tog14.setBackgroundResource(R.drawable.icono_sx_sexo_sin_proteccion_check);
                } else {
                    tog14.setBackgroundResource(R.drawable.icono_sx_sexo_sin_proteccion);
                }

            }
        });


        /*
                ESTADO DE ANIMO (SEGUNDO)
         */
        //  amorosa
        tog21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog21.isChecked()){
                    tog21.setBackgroundResource(R.drawable.icono_ea_amoroso_check);
                } else {
                    tog21.setBackgroundResource(R.drawable.icono_ea_amoroso);
                }

            }
        });

        //  asustada
        tog22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog22.isChecked()){
                    tog22.setBackgroundResource(R.drawable.icono_ea_asustado_check);
                } else {
                    tog22.setBackgroundResource(R.drawable.icono_ea_asustado);
                }

            }
        });

        //  coqueta
        tog23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog23.isChecked()){
                    tog23.setBackgroundResource(R.drawable.icono_ea_coqueto_check);
                } else {
                    tog23.setBackgroundResource(R.drawable.icono_ea_coqueto);
                }

            }
        });

        //  enojado
        tog24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog24.isChecked()){
                    tog24.setBackgroundResource(R.drawable.icono_ea_enojado_check);
                } else {
                    tog24.setBackgroundResource(R.drawable.icono_ea_enojado);
                }

            }
        });

        //  estresado
        tog25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog25.isChecked()){
                    tog25.setBackgroundResource(R.drawable.icono_ea_estresado_check);
                } else {
                    tog25.setBackgroundResource(R.drawable.icono_ea_estresado);
                }

            }
        });

        //  llorosa
        tog26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog26.isChecked()){
                    tog26.setBackgroundResource(R.drawable.icono_ea_lloroso_check);
                } else {
                    tog26.setBackgroundResource(R.drawable.icono_ea_lloroso);
                }

            }
        });

        //  relajada
        tog27.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog27.isChecked()){
                    tog27.setBackgroundResource(R.drawable.icono_ea_relajada_check);
                } else {
                    tog27.setBackgroundResource(R.drawable.icono_ea_relajada);
                }

            }
        });


        /*
                SINTOMAS (TERCERO)
         */
        //  acne
        tog31.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog31.isChecked()){
                    tog31.setBackgroundResource(R.drawable.icono_s_acne_check);
                } else {
                    tog31.setBackgroundResource(R.drawable.icono_s_acne);
                }

            }
        });

        //  dolor de cabeza
        tog32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog32.isChecked()){
                    tog32.setBackgroundResource(R.drawable.icono_s_dolor_de_cabeza_check);
                } else {
                    tog32.setBackgroundResource(R.drawable.icono_s_dolor_de_cabeza);
                }

            }
        });

        //  fatiga
        tog33.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog33.isChecked()){
                    tog33.setBackgroundResource(R.drawable.icono_s_fatiga_check);
                } else {
                    tog33.setBackgroundResource(R.drawable.icono_s_fatiga);
                }

            }
        });

        //  vomito
        tog34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog34.isChecked()){
                    tog34.setBackgroundResource(R.drawable.icono_s_vomito_check);
                } else {
                    tog34.setBackgroundResource(R.drawable.icono_s_vomito);
                }

            }
        });

        //  colicos
        tog35.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog35.isChecked()){
                    tog35.setBackgroundResource(R.drawable.icono_s_colicos_check);
                } else {
                    tog35.setBackgroundResource(R.drawable.icono_s_colicos);
                }

            }
        });

        //  dolor de espalda
        tog36.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog36.isChecked()){
                    tog36.setBackgroundResource(R.drawable.icono_s_dolor_de_espalda_check);
                } else {
                    tog36.setBackgroundResource(R.drawable.icono_s_dolor_de_espalda);
                }

            }
        });

        //  inflamacion
        tog37.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog37.isChecked()){
                    tog37.setBackgroundResource(R.drawable.icono_s_inflamacion_check);
                } else {
                    tog37.setBackgroundResource(R.drawable.icono_s_inflamacion);
                }

            }
        });

        //  senos sensibles
        tog38.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog38.isChecked()){
                    tog38.setBackgroundResource(R.drawable.icono_s_senos_sensibles_check);
                } else {
                    tog38.setBackgroundResource(R.drawable.icono_s_senos_sensibles);
                }

            }
        });

        /*
                FLUJO VAGINAL (CUARTO)
         */
        //  cremoso
        tog41.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog41.isChecked()){
                    tog41.setBackgroundResource(R.drawable.icono_f_cremoso_check);
                } else {
                    tog41.setBackgroundResource(R.drawable.icono_f_cremoso);
                }

            }
        });

        //  clara
        tog42.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog42.isChecked()){
                    tog42.setBackgroundResource(R.drawable.icono_f_clara_check);
                } else {
                    tog42.setBackgroundResource(R.drawable.icono_f_clara);
                }

            }
        });

        //  pegajoso
        tog43.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog43.isChecked()){
                    tog43.setBackgroundResource(R.drawable.icono_f_pegajoso_check);
                } else {
                    tog43.setBackgroundResource(R.drawable.icono_f_pegajoso);
                }

            }
        });

        //  seco
        tog44.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tog44.isChecked()){
                    tog44.setBackgroundResource(R.drawable.icono_f_seco_check);
                } else {
                    tog44.setBackgroundResource(R.drawable.icono_f_seco);
                }

            }
        });


    }

    private void recogerYguardar() {

        String idUser = mAuth.getCurrentUser().getUid();

        if(fecha == null){
            Toast.makeText(this, "Selecciona una fecha", Toast.LENGTH_SHORT).show();
        } else {

            // nombre de las colecciones
            String bloq1 = "1_actividadSex";
            String bloq2 = "2_estadoAnimo";
            String bloq3 = "3_sintomas";
            String bloq4 = "4_flujVag";

            // reseteo de colecciones
            resetearColeccion(bloq1);
            resetearColeccion(bloq2);
            resetearColeccion(bloq3);
            resetearColeccion(bloq4);

            // variable con la que se cuenta el numero de sintomas registrados por cada bloque
            int cant1 = 0;
            int cant2 = 0;
            int cant3 = 0;
            int cant4 = 0;


            /*
                    BLOQUE 1
             */
            if(tog11.isChecked()){

                tog11.setText("Deseo sexual");
                cant1++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant1, tog11.getText().toString());

                db.collection(bloq1).document(idUser).update(sintomas);
            }

            if (tog12.isChecked()){

                tog12.setText("Masturbación");
                cant1++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant1, tog12.getText().toString());

                db.collection(bloq1).document(idUser).update(sintomas);
            }

            if (tog13.isChecked()){

                tog13.setText("Sexo con protección");
                cant1++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant1, tog13.getText().toString());

                db.collection(bloq1).document(idUser).update(sintomas);
            }

            if (tog14.isChecked()){

                tog14.setText("Sexo sin protección");
                cant1++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant1, tog14.getText().toString());

                db.collection(bloq1).document(idUser).update(sintomas);
            }

            /*
                    BLOQUE 2
             */
            if(tog21.isChecked()){

                tog21.setText("Amorosa");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog21.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog22.isChecked()){

                tog22.setText("Asustada");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog22.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog23.isChecked()){

                tog23.setText("Coqueta");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog23.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog24.isChecked()){

                tog24.setText("Enojada");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog24.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog25.isChecked()){

                tog25.setText("Estresada");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog25.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog26.isChecked()){

                tog26.setText("Llorosa");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog26.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            if(tog27.isChecked()){

                tog27.setText("Relajada");
                cant2++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant2, tog27.getText().toString());

                db.collection(bloq2).document(idUser).update(sintomas);
            }

            /*
                    BLOQUE 3
             */
            if(tog31.isChecked()){

                tog31.setText("Acne");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog31.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog32.isChecked()){

                tog32.setText("Dolor de cabeza");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog32.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog33.isChecked()){

                tog33.setText("Fatiga");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog33.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog34.isChecked()){

                tog34.setText("Vomito");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog34.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog35.isChecked()){

                tog35.setText("Colicos");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog35.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog36.isChecked()){

                tog36.setText("Dolor de espalda");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog36.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog37.isChecked()){

                tog37.setText("Inlaflamacion");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog37.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            if(tog38.isChecked()){

                tog38.setText("Senos sensibles");
                cant3++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant3, tog38.getText().toString());

                db.collection(bloq3).document(idUser).update(sintomas);
            }

            /*
                    BLOQUE 4
             */
            if(tog41.isChecked()){

                tog41.setText("Cremoso");
                cant4++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant4, tog41.getText().toString());

                db.collection(bloq4).document(idUser).update(sintomas);
            }

            if(tog42.isChecked()){

                tog42.setText("Clara de huevo");
                cant4++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant4, tog42.getText().toString());

                db.collection(bloq4).document(idUser).update(sintomas);
            }

            if(tog43.isChecked()){

                tog43.setText("Pegajoso");
                cant4++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant4, tog43.getText().toString());

                db.collection(bloq4).document(idUser).update(sintomas);
            }

            if(tog44.isChecked()){

                tog44.setText("Seco");
                cant4++;

                // CODIGO DE INSERTE EN BASE DE DATOS
                Map<String, Object> sintomas = new HashMap<>();
                sintomas.put("descripcion_" + cant4, tog44.getText().toString());

                db.collection(bloq4).document(idUser).update(sintomas);
            }

            Toast.makeText(this, "Sintomas guardados correctamente", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Sintomas.this, Navigation.class));

        }

    }

    private void resetearColeccion(String bloq) {

        String idUser = mAuth.getCurrentUser().getUid();
        db.collection(bloq).document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        Map<String, Object> tabla = new HashMap<>();
        tabla.put("fecha", fecha);
        db.collection(bloq).document(idUser).set(tabla);
    }


}