package com.example.culturavisual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Cuestionarios extends AppCompatActivity {

    Button culturaGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios);

        culturaGeneral = findViewById(R.id.btn_culturaGeneral);
    }

    @Override
    protected void onStart() {
        super.onStart();



        culturaGeneral.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Cuestionarios.this, Preguntas.class);
                Cuestionarios.this.startActivity(myIntent);
            }
        });
    }
}
