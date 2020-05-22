package com.example.culturavisual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Preguntas extends AppCompatActivity {

    private TextView nScoreView;
    private TextView nPregunta;
    private Button botonAceptar;

    private TextView nR1, nR2, nR3, nR4;

    private String respuesta;
    private int score;

    private RecyclerView mRecyclerView;
    private AdapterPreguntas mAdapterPreguntas;
    private ArrayList<preguntasObj> mPreguntasList;
    private ArrayList<String> mCorrectasList;
    private Usuarios loggedUser;
    private CuestionarioObj cuestionario;
    private Integer correctAnswers[];


    FirebaseFirestore db;
    CollectionReference quizCollection;
    CollectionReference usuarioQuiz;

    //private Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");
        cuestionario = (CuestionarioObj)getIntent().getSerializableExtra("quiz");

        db = FirebaseFirestore.getInstance();
        quizCollection = db.collection(cuestionario.getCuestionarioID());
        usuarioQuiz = db.collection("usuarioQuiz");



        nScoreView =  findViewById(R.id.txt_score);
        nPregunta = findViewById(R.id.txt_pregunta);
        botonAceptar = findViewById(R.id.btn_aceptar);


        nR1 = findViewById(R.id.r1);
        nR2 = findViewById(R.id.r2);
        nR3 = findViewById(R.id.r3);
        nR4 = findViewById(R.id.r4);

        mPreguntasList = new ArrayList<>();
        mCorrectasList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view_preguntas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        parsePreguntas();

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer chosenAnswers[] = mAdapterPreguntas.getChosenAnswers();
                correctAnswers = new Integer[mPreguntasList.size()];
                Arrays.fill(correctAnswers,new Integer(0));

                for(int i=0; i<mPreguntasList.size(); i++){
                    if(Integer.valueOf(mPreguntasList.get(i).getCorrecta()) == chosenAnswers[i]){
                        correctAnswers[i] = 1;
                    }
                }

                saveAnswersToDatabase();

                Intent myIntent = new Intent(Preguntas.this, ResultadosCuestionario.class);
                myIntent.putExtra("user", loggedUser);
                myIntent.putExtra("answers", correctAnswers);
                Preguntas.this.startActivity(myIntent);

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
                        mAdapterPreguntas = new AdapterPreguntas(Preguntas.this, mPreguntasList, loggedUser);
                        mRecyclerView.setAdapter(mAdapterPreguntas);

                    }
                });
    }

    private void saveAnswersToDatabase(){

        final UsuarioCuestionario usuarioCuestionario = new UsuarioCuestionario();

        Integer correct = 0;
        Integer incorrect = 0;

        for (int i=0; i<correctAnswers.length; i++){
            if(correctAnswers[i] == 0){
                incorrect += 1;
            }
            else{
                correct += 1;
            }
        }

        usuarioCuestionario.setUser(loggedUser.getUsuario());
        usuarioCuestionario.setQuizID(cuestionario.getCuestionarioID());
        usuarioCuestionario.setCorrectAnswers(correct);
        usuarioCuestionario.setWrongAnswers(incorrect);
        usuarioCuestionario.setScore(correct*100);


        final DocumentReference usuarioQuizRef = usuarioQuiz.document(usuarioCuestionario.getUser()+usuarioCuestionario.getQuizID());

        usuarioQuizRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Toast.makeText(getApplicationContext(),
                            "UPDATE!", Toast.LENGTH_SHORT).show();
                    if(Integer.valueOf(documentSnapshot.getString("score")) < usuarioCuestionario.getScore()){
                        usuarioQuizRef.update("correctAnswers", toString().valueOf(usuarioCuestionario.getCorrectAnswers()));
                        usuarioQuizRef.update("wrongAnswers", toString().valueOf(usuarioCuestionario.getWrongAnswers()));
                        usuarioQuizRef.update("score", toString().valueOf(usuarioCuestionario.getScore()));
                    }

                } else{

                    Map<String, Object> usuarioEntry = new HashMap<>();
                    usuarioEntry.put("user", usuarioCuestionario.getUser());
                    usuarioEntry.put("quizID", usuarioCuestionario.getQuizID());
                    usuarioEntry.put("score", toString().valueOf(usuarioCuestionario.getScore()));
                    usuarioEntry.put("correctAnswers", toString().valueOf(usuarioCuestionario.getCorrectAnswers()));
                    usuarioEntry.put("wrongAnswers", toString().valueOf(usuarioCuestionario.getWrongAnswers()));

                    usuarioQuizRef.set(usuarioEntry);


                    Toast.makeText(getApplicationContext(),
                            "Entrada creada.", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}
