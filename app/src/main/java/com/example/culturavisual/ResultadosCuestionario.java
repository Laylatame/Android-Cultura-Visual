package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

public class ResultadosCuestionario extends AppCompatActivity {

    private Usuarios loggedUser;
    private Integer answersBool[];
    private Integer chosenAnswers[];
    private CuestionarioObj cuestionario;

    private TextView textViewCorrectas;
    private TextView textViewIncorrectas;
    private TextView textViewScore;
    private Button buttonRegresarResultados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_cuestionario);

        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");
        answersBool = (Integer[])getIntent().getSerializableExtra("answers");
        cuestionario = (CuestionarioObj)getIntent().getSerializableExtra("cuestionario");
        chosenAnswers = (Integer[])getIntent().getSerializableExtra("chosenAnswers");

        textViewCorrectas = findViewById(R.id.respuestasCorrectas);
        textViewIncorrectas = findViewById(R.id.respuestasIncorrectas);
        textViewScore = findViewById(R.id.showScoreResultados);
        buttonRegresarResultados = findViewById(R.id.buttonRegresarResultados);

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

        buildPieChart(correct, incorrect);

        buttonRegresarResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultadosCuestionario.this, Preguntas.class);
                myIntent.putExtra("user", loggedUser);
                myIntent.putExtra("quiz", cuestionario);
                myIntent.putExtra("showResults", true);
                myIntent.putExtra("answers", answersBool);
                myIntent.putExtra("resultsChosenAnswers", chosenAnswers);
                setResult(RESULT_OK, myIntent);
                ResultadosCuestionario.this.startActivity(myIntent);
            }
        });

    }

    private void buildPieChart(int correct, int incorrect){
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(correct, "Correctas"));
        pieEntries.add(new PieEntry(incorrect, "Incorrectas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "Resultados");
        dataSet.setColors(COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = findViewById(R.id.pieChartResultados);
        chart.setData(data);
        chart.animateY(1000);
        chart.getDescription().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.invalidate();
    }

    public static final int[] COLORS = {
            Color.rgb(50, 206, 39), Color.rgb(255,40,0)
    };
}
