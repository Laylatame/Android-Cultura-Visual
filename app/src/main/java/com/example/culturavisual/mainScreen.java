package com.example.culturavisual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class mainScreen extends AppCompatActivity {

    Button buttonMisEstadisticas;
    Button buttonCuestionarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        buttonMisEstadisticas = findViewById(R.id.botonEstadisticas);
        buttonCuestionarios = findViewById(R.id.botonCuestionarios);

    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonMisEstadisticas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Load activity mis estadisticas
            }
        });

        buttonCuestionarios.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mainScreen.this, Cuestionarios.class);
                mainScreen.this.startActivity(myIntent);
            }
        });
    }
}

