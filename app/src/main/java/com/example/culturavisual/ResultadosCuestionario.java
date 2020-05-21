package com.example.culturavisual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadosCuestionario extends AppCompatActivity {

    private Usuarios loggedUser;
    private Integer answers[];

    private TextView textViewCorrectas;
    private TextView textViewIncorrectas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_cuestionario);

        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");
        answers = (Integer[])getIntent().getSerializableExtra("answers");

        textViewCorrectas = findViewById(R.id.respuestasCorrectas);
        textViewIncorrectas = findViewById(R.id.respuestasIncorrectas);

        Integer correct = 0;
        Integer incorrect = 0;

        for (int i=0; i<answers.length; i++){
            if(answers[i] == 0){
                incorrect += 1;
            }
            else{
                correct += 1;
            }
        }

        textViewCorrectas.setText(String.valueOf(correct));
        textViewIncorrectas.setText(String.valueOf(incorrect));

        //Toast.makeText(getApplicationContext(), "Correctas: " + correct + " Incorrectas: " + incorrect, Toast.LENGTH_LONG).show();

    }
}
