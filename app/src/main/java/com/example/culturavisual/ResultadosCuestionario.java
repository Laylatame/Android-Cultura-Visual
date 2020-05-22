package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.transform.Result;

public class ResultadosCuestionario extends AppCompatActivity {

    private Usuarios loggedUser;
    private Integer answersBool[];
    private Integer chosenAnswers[];
    private CuestionarioObj cuestionario;

    private RecyclerView mRecyclerView;
    private AdapterPreguntas mAdapterPreguntas;

    private TextView textViewCorrectas;
    private TextView textViewIncorrectas;
    private TextView textViewScore;
    private Button buttonRegresarResultados;

    private ArrayList<preguntasObj> mPreguntasList;

    FirebaseFirestore db;
    CollectionReference quizCollection;
    CollectionReference usuarioQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_cuestionario);

        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");
        answersBool = (Integer[])getIntent().getSerializableExtra("answers");
        cuestionario = (CuestionarioObj)getIntent().getSerializableExtra("cuestionario");
        chosenAnswers = (Integer[])getIntent().getSerializableExtra("chosenAnswers");



        db = FirebaseFirestore.getInstance();
        quizCollection = db.collection(cuestionario.getCuestionarioID());
        usuarioQuiz = db.collection("usuarioQuiz");

        textViewCorrectas = findViewById(R.id.respuestasCorrectas);
        textViewIncorrectas = findViewById(R.id.respuestasIncorrectas);
        textViewScore = findViewById(R.id.showScoreResultados);
        buttonRegresarResultados = findViewById(R.id.buttonRegresarResultados);

        /*
        mRecyclerView = findViewById(R.id.recycler_view_preguntas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


         */
        Integer correct = 0;
        Integer incorrect = 0;

        for (int i=0; i<answersBool.length; i++){
            if(answersBool[i] == 0){
                incorrect += 1;
            }
            else{
                correct += 1;
            }
        }

        int scoreInt = correct * 100;
        String score = "Score: " + String.valueOf(scoreInt);

        textViewCorrectas.setText(String.valueOf(correct));
        textViewIncorrectas.setText(String.valueOf(incorrect));
        textViewScore.setText(String.valueOf(score));


        buttonRegresarResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultadosCuestionario.this, Preguntas.class);
                myIntent.putExtra("user", loggedUser);
                myIntent.putExtra("quiz", cuestionario);
                myIntent.putExtra("showResults", true);
                myIntent.putExtra("answers", answersBool);
                myIntent.putExtra("resultsChosenAnswers", chosenAnswers);
                ResultadosCuestionario.this.startActivity(myIntent);
            }
        });

    }

    public void parsePreguntas(){

        quizCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String dbNombrePregunta = document.getString("pregunta");
                                String dbCorrecta = document.getString("correctAnswer");

                                Map<String, Map<String, Map<String, Map<String, String>>>> respuestas  = (Map) document.getData();
                                String contenidoR1 = respuestas.get("respuestas").get("0").get("contenido").get("contenido");
                                String boolR1 = respuestas.get("respuestas").get("0").get("isImage").get("isImage");
                                String contenidoR2 = respuestas.get("respuestas").get("1").get("contenido").get("contenido");
                                String boolR2 = respuestas.get("respuestas").get("1").get("isImage").get("isImage");
                                String contenidoR3 = respuestas.get("respuestas").get("2").get("contenido").get("contenido");
                                String boolR3 = respuestas.get("respuestas").get("2").get("isImage").get("isImage");
                                String contenidoR4 = respuestas.get("respuestas").get("3").get("contenido").get("contenido");
                                String boolR4 = respuestas.get("respuestas").get("3").get("isImage").get("isImage");
                                boolean isImageR1 = Boolean.parseBoolean(boolR1);
                                boolean isImageR2 = Boolean.parseBoolean(boolR2);
                                boolean isImageR3 = Boolean.parseBoolean(boolR3);
                                boolean isImageR4 = Boolean.parseBoolean(boolR4);

                                preguntasObj pregunta = new preguntasObj(dbNombrePregunta, contenidoR1, isImageR1, contenidoR2, isImageR2, contenidoR3, isImageR3, contenidoR4, isImageR4, dbCorrecta);

                                mPreguntasList.add(pregunta);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No hay preguntas", Toast.LENGTH_SHORT).show();
                        }
                        //mAdapterPreguntas = new AdapterPreguntas(ResultadosCuestionario.this, mPreguntasList, loggedUser, true, answers);
                        mRecyclerView.setAdapter(mAdapterPreguntas);
                    }
                });
    }
}
