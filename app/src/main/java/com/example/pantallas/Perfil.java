package com.example.pantallas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    // usuarios
    ImageView imgUser;
    TextView editNombre1;
    TextView editEmailPerfil;

    ImageView ciclo2;
    ImageView misDatos2;
    ImageView politica2;
    ImageView contactanos2;


    ImageView btn_backIndex;
    Button btn_cerrarSesionPerfil;
    TextView btn_eliminarCuenta;

    StorageReference storageReference;

    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    String photo = "photo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();



        setup();

        // usuarios
        editNombre1 = findViewById(R.id.editNombre1);
        editEmailPerfil = findViewById(R.id.editEmailPerfil);

        imgUser = findViewById(R.id.imgUser);
        imgUser.setImageResource(R.drawable.img_perfil);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });


        btn_backIndex = findViewById(R.id.btn_backIndex);
        btn_backIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Perfil.this, Navigation.class));

            }
        });

        ciclo2 = findViewById(R.id.ciclo2);
        ciclo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, CambiarMiCiclo.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        misDatos2 = findViewById(R.id.misDatos2);
        misDatos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, CambiarMisDatos.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        politica2 = findViewById(R.id.politica2);
        politica2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://hannahpolitica.weebly.com/poliacutetica-de-privacidad.html";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        contactanos2 = findViewById(R.id.contactanos2);
        contactanos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://hannahpolitica.weebly.com/contactanos.html";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        btn_cerrarSesionPerfil = findViewById(R.id.btn_cerrarSesionPerfil);
        btn_cerrarSesionPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cerrarSesion();

            }
        });

        String idUser = mAuth.getCurrentUser().getUid();

        btn_eliminarCuenta = findViewById(R.id.btn_eliminarCuenta);
        btn_eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser != null){
                    eliminarCuenta();
                }

            }
        });

    }

    private void setup() {

        recogeDatosUser();
        recogerImg();

    }

    private void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, COD_SEL_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            image_url = data.getData();
            subirPhoto(image_url);
        }
    }

    private void subirPhoto(Uri image_url){
        String idUser = mAuth.getCurrentUser().getUid();
        Toast.makeText(this, "Actualizando foto", Toast.LENGTH_SHORT).show();
        StorageReference reference = storageReference.child("img").child(idUser + ".jpg");
        reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()){
                    if (uriTask.isSuccessful()) {
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String download_uri = uri.toString();
                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("photo", download_uri);
                                db.collection("usuarios").document(idUser).update(usuario);
                                Toast.makeText(Perfil.this, "Foto Actualizada", Toast.LENGTH_SHORT).show();

                                recogerImg();

                            }
                        });
                    }
                }
            }
        });
    }

    public void recogeDatosUser(){

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla usuarios
        DocumentReference docRefUser = db.collection("usuarios").document(idUser);

        docRefUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    String nombre = documentSnapshot.getString("nombre");
                    editNombre1.setText(nombre);

                    String email = documentSnapshot.getString("email");
                    editEmailPerfil.setText(email);

                    Picasso.with(Perfil.this).load(photo).resize(130, 130).into(imgUser);

                } else {
                    Toast.makeText(Perfil.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void recogerImg() {
        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla usuarios
        DocumentReference docRefUser = db.collection("usuarios").document(idUser);

        docRefUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {
                    String photo = documentSnapshot.getString("photo");
                    if (!photo.equals("")){
                        Picasso.with(Perfil.this).load(photo).resize(130, 130).into(imgUser);
                    } else if (photo.equals("")){
                        imgUser.setImageResource(R.drawable.img_perfil);
                    }
                } else {
                    Toast.makeText(Perfil.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void cerrarSesion(){

        mAuth.signOut();

        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(Perfil.this, Bienvenida.class));

    }

    public void eliminarCuenta(){

        String idUser = mAuth.getCurrentUser().getUid();

        // Recoger datos de tabla cicloMenstrual
        DocumentReference users = db.collection("usuarios").document(idUser);

        users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                // Verificar si el documento existe
                if (documentSnapshot.exists()) {

                    FirebaseUser user = mAuth.getCurrentUser();

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Perfil.this);
                    alerta.setMessage("¿Seguro que desea eliminar su cuenta?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // elimina
                                    borrarColecciones();
                                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Perfil.this, "Cuenta Eliminada con exito", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                // CUENTA ELIMINADA CON DATOS INCLUIDOS
                                                startActivity(new Intent(Perfil.this, Bienvenida.class));
                                            } else {
                                                Toast.makeText(Perfil.this, "Error al autenticar", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Eliminar Cuenta");
                    titulo.show();

                } else {
                    Toast.makeText(Perfil.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void borrarColecciones() {

        String idUser = mAuth.getCurrentUser().getUid();
        db.collection("cicloMenstrual").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        db.collection("usuarios").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        db.collection("1_actividadSex").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        db.collection("2_estadoAnimo").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        db.collection("3_sintomas").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

        db.collection("4_flujVag").document(idUser).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("DOCUMENTO ELIMINADO");
            }
        });

    }

}