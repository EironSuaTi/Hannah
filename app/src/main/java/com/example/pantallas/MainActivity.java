package com.example.pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView ic_hannah;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ic_hannah = findViewById(R.id.ic_hannah);

        mAuth = FirebaseAuth.getInstance();

        Glide.with(this)
                .asGif()
                .load(R.drawable.hannah_gif) // Reemplaza "your_gif_resource" con el ID de tu recurso GIF
                .into(new ImageViewTarget<GifDrawable>(ic_hannah) {
                    @Override
                    protected void setResource(GifDrawable resource) {
                        // Muestra el GIF en el ImageView
                        ic_hannah.setImageDrawable(resource);

                        // Inicia el retraso después de que el GIF se haya mostrado
                        int retrasoEnMilisegundos = 2500; // 2000 milisegundos = 2 segundos
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Código a ejecutar después del retraso, como iniciar la transición del MotionLayout
                                start();
                            }
                        }, retrasoEnMilisegundos);
                    }
                });


    }

    public void start (){

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, Navigation.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        } else {
            startActivity(new Intent(this, Bienvenida.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        }

    }
}