package com.example.culturavisual;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Preguntas extends AppCompatActivity {

    private TextView nScoreView;
    private TextView nPregunta;

    private Button nR1, nR2, nR3, nR4, nCorrecto;

    private String respuesta;
    private int score;

    private RecyclerView mRecyclerView;
    private AdapterPreguntas mAdapterPreguntas;
    private ArrayList<preguntasObj> mPreguntasList;
    private RequestQueue mRequestQueue;

    //private Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        nScoreView =  findViewById(R.id.txt_score);
        nPregunta = findViewById(R.id.txt_pregunta);

        nR1 = findViewById(R.id.btn_r1);
        nR2 = findViewById(R.id.btn_r2);
        nR3 = findViewById(R.id.btn_r3);
        nR4 = findViewById(R.id.btn_r4);

        //nCorrecto =  findViewById(R.id.btn_correct);

        mPreguntasList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view_preguntas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       // VolleyLog.DEBUG = true;
        //mRequestQueue = Volley.newRequestQueue(this);
        parsePreguntas();


    }


    public void parsePreguntas(){

        int i = 0;
    for(int i1 = 0; i1 <= 5; i1++) {
        String pregunta = "prueba";
        mPreguntasList.add(new preguntasObj(pregunta, nR1,nR2,nR3,nR4/*,nCorrecto*/));
        i++;
    }
        mAdapterPreguntas = new AdapterPreguntas(Preguntas.this, mPreguntasList, i);
        mRecyclerView.setAdapter(mAdapterPreguntas);

   //     mRequestQueue.add(request);
    }
}
