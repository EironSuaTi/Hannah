package com.example.pantallas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Fragmento_Index extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    View vista;

    TextView txtDia2;
    TextView txtInfoDias;
    TextView txtInfoDias2;

    ImageView btn_sintomas;
    TextView btn_verSintomas;

    ShapeableImageView btn_perfil2;

    String duracionPeriodo;
    String duracionCiclo;
    String diaComienzo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragmento__index, container, false);

        txtInfoDias = vista.findViewById(R.id.txtInfoDias);
        txtInfoDias2 = vista.findViewById(R.id.txtInfoDias2);

        setUp();


        btn_perfil2 = vista.findViewById(R.id.btn_perfil2);
        btn_perfil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Perfil.class));
            }
        });

        btn_sintomas = vista.findViewById(R.id.btn_sintomas);
        btn_sintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Sintomas.class));
            }
        });

        btn_verSintomas = vista.findViewById(R.id.btn_verSintomas);
        btn_verSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SintomasVista.class));
            }
        });

        return vista;

    }

    private void setUp() {

        String dia = "dd";
        String mes = "MMMM";

        txtDia2 = vista.findViewById(R.id.txtDia2);

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dia + " " + mes, Locale.getDefault());//"dd 'de' MMMM 'de' yyyy"
        String formattedDate = dateFormat.format(currentDate);

        txtDia2.setText(formattedDate);

        // RECOGEMOS LOS DATOS DE LA BASE DE DATOS

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference docRefCiclo = db.collection("cicloMenstrual").document(idUser);

        docRefCiclo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // CALCULO DE DIAS DEL CICLO

                LocalDate hoyNow = LocalDate.now();
                // Verificar si el documento existe
                LocalDate diaComienzoLocal = null;
                if (documentSnapshot.exists()) {

                    duracionPeriodo = documentSnapshot.getString("duracionPeriodo");
                    duracionCiclo = documentSnapshot.getString("duracionCiclo");
                    diaComienzo = documentSnapshot.getString("diaComienzo");


                    System.out.println("/------------------------ 1 ------------------------/");

                    System.out.println("DuracionCiclo: " + duracionCiclo);
                    System.out.println("Duracion Periodo: " + duracionPeriodo);
                    System.out.println("DiaComienzo: " + diaComienzo);

                    if (diaComienzo.equals("")) {

                        txtInfoDias.setText("Introduzca una fecha válida para el comienzo de su ciclo");
                        txtInfoDias2.setText("No hay predicción de días");

                    } else {

                        // convertimos la fecha de string a localdate
                        diaComienzoLocal = LocalDate.parse(diaComienzo);
                        // convertimos el numero de dias de string a int
                        int duracionCicloInt = Integer.parseInt(duracionCiclo);

                        System.out.println("/------------------------ 2 ------------------------/");
                        ArrayList<LocalDate> lista = new ArrayList<>();

//                    LocalDate hoyNow = LocalDate.now();

                        // Calcular los días del período en función de duracionPeriodo
                        for (int i = 0; i < Integer.parseInt(duracionPeriodo); i++) {

                            LocalDate fechaPeriodo = diaComienzoLocal.plusDays(i);
                            lista.add(fechaPeriodo);
                            // System.out.println("Día " + (i + 1) + " del período: " + diaPeriodo);

                            String fechaPeriodoString = fechaPeriodo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            Map<String, Object> cicloMens = new HashMap<>();
                            cicloMens.put("fechaPeriodo_" + (i + 1), fechaPeriodoString);

                            db.collection("cicloMenstrual").document(idUser).update(cicloMens);

                        }

                        System.out.println("LISTA: " + lista);

                        LocalDate ultimoDiaPeriodo = lista.get(lista.size() - 1);
                        System.out.println("Ultimo día de Periodo: " + ultimoDiaPeriodo);

                        System.out.println("/------------------------ 3 ------------------------/");
                        // Realizamos operaciones con las fechas para sacar los dias deseados
                        LocalDate diaDespuesPeriodo = ultimoDiaPeriodo.plusDays(1);
                        LocalDate ultimoDiaCiclo = diaComienzoLocal.plusDays(duracionCicloInt - 1);
                        LocalDate diaCuentaAtras = ultimoDiaCiclo.minusDays(4);
                        System.out.println("Día después de periodo: " + diaDespuesPeriodo);
                        System.out.println("Ultimo día de ciclo: " + ultimoDiaCiclo);
                        System.out.println("Día para la cuenta atrás: " + diaCuentaAtras + "\n");

                        LocalDate sigDiaComienzo = ultimoDiaCiclo.plusDays(1);
                        String sigDiaComienzoString = sigDiaComienzo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        System.out.println("Siguiente día comienzo: " + sigDiaComienzo);

                        System.out.println("-------- Bucle Final --------");
                        // Bucle que realiza toda la prediccion e informacion sobre nuestro ciclo/periodo
                        for (int i = 0; i < lista.size(); i++) {

                            int nDia = i;
                            LocalDate diaLista = lista.get(i);

                            // si estoy en un mismo día de la lista (dias de periodo)
                            if (diaLista.isEqual(hoyNow)) {
                                // reviso y digo en que día estoy
                                String txt = "Hoy es tu " + (nDia + 1) + "º día de periodo";

                                System.out.println(txt);

                                txtInfoDias.setText(txt);

                                break;
                            } else if (ultimoDiaPeriodo.isBefore(hoyNow) && diaCuentaAtras.isAfter(hoyNow)) {   // Si nos encontramos en un día lejano al proximo dia de periodo
                                //
                                String sigDiaComienzoString2 = sigDiaComienzo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                String txt = "Tu siguiente periodo comienza el día: " + sigDiaComienzoString2;
                                System.out.println(txt);

                                txtInfoDias.setText(txt);

                                break;
                            } else if (diaCuentaAtras.isEqual(hoyNow) || hoyNow.isAfter(diaCuentaAtras) && hoyNow.isBefore(ultimoDiaCiclo) || ultimoDiaCiclo.isEqual(hoyNow)) {     // Si estamos a 5 dias de que comienze nuestro periodo

                                int diasCuenta = hoyNow.until(ultimoDiaCiclo).getDays() + 1;
                                String txt = "Quedan " + diasCuenta + " días para tu próximo periodo";
                                System.out.println(txt);

                                txtInfoDias.setText(txt);

                                break;
                            } else if (hoyNow.isEqual(sigDiaComienzo)) {    // Si nos encontramos en el mismo día en el que comienza nuestro siguiente periodo

                                String txt = "Hoy es tu " + (nDia + 1) + "º día de periodo";

                                // actualizamos ese día como nuestro nuevo día de comienzo de ciclo/periodo
                                Map<String, Object> cicloMens = new HashMap<>();
                                cicloMens.put("diaComienzo", sigDiaComienzoString);

                                db.collection("cicloMenstrual").document(idUser).update(cicloMens);

                                System.out.println("Nuevo día comienzo: " + diaComienzo);
                                System.out.println(txt);
                                System.out.println("adios");

                                txtInfoDias.setText(txt);

                                System.out.println("--------------------------------------------------------------------------------------");
                                break;
                            }

                        }

                        // CALCULO DE DIAS FERTILES

                        double duracionCicloDouble = Double.parseDouble(duracionCiclo);
                        int DiasMitadCiclo = (int) duracionCicloDouble / 2;

                        System.out.println("DIAS MITAD CICLO " + DiasMitadCiclo);

                        LocalDate diaOvulacionLocal = diaComienzoLocal.plusDays(DiasMitadCiclo - 1);
                        System.out.println(diaOvulacionLocal);

                        if (hoyNow.equals(diaOvulacionLocal.minusDays(2)) || hoyNow.equals(diaOvulacionLocal.minusDays(1))){
                            txtInfoDias2.setText("Alta probabilidad de quedar embarazada");
                            System.out.println("dias antes");
                        } else if(hoyNow.equals(diaOvulacionLocal)) {
                            txtInfoDias2.setText("Hoy es tu día de maxima probabilidad de embarazo");
                            System.out.println("hoy es el dia");
                        } else if (hoyNow.equals(diaOvulacionLocal.plusDays(1)) || hoyNow.equals(diaOvulacionLocal.plusDays(2))){
                            txtInfoDias2.setText("Alta probabilidad de quedar embarazada");
                            System.out.println("dias despues");
                        } else if (txtInfoDias.getText().toString().equals("Introduzca una fecha válida para el comienzo de su ciclo")){
                            txtInfoDias2.setText("No hay predicción de días");
                        }else {
                            txtInfoDias2.setText("Baja probabilidad de quedar embarazada");
                        }

                    }



                } else {
                    Toast.makeText(getActivity(), "El documento cicloMenstrual no existe", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}