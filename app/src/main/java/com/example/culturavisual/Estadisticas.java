package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estadisticas extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterEstadisticas mAdapterEstadisticas;
    private ArrayList<CuestionarioObj> mCuestionariosList;
    private ArrayList<UsuarioCuestionario> mProgressUser;
    Usuarios loggedUser;

    FirebaseFirestore db;
    CollectionReference quizesCollection;
    CollectionReference usuarioQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        db = FirebaseFirestore.getInstance();
        quizesCollection = db.collection("quizes");
        usuarioQuiz = db.collection("usuarioQuiz");

        mRecyclerView = findViewById(R.id.recyclerViewEstadisticas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");

        mCuestionariosList = new ArrayList<>();
        mProgressUser = new ArrayList<>();

        getCuestionariosFromDB();
    }

    public void getCuestionariosFromDB(){

        // Create a query against the collection.
        Query query = usuarioQuiz.whereEqualTo("user", loggedUser.getUsuario());

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<DocumentSnapshot> snapshotsList = documentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotsList) {
                    String dbUsuario = snapshot.getString("user");
                    String dbQuizID = snapshot.getString("quizID");
                    int dbCorrectas = Integer.valueOf(snapshot.getString("correctAnswers"));
                    int dbIncorrectas = Integer.valueOf(snapshot.getString("wrongAnswers"));
                    int dbScore = Integer.valueOf(snapshot.getString("score"));

                    UsuarioCuestionario current = new UsuarioCuestionario(dbUsuario, dbQuizID, dbCorrectas, dbIncorrectas, dbScore);
                    mProgressUser.add(current);
                }

                mAdapterEstadisticas = new AdapterEstadisticas(Estadisticas.this, loggedUser, mProgressUser);
                buildPieChart(mProgressUser);
                mRecyclerView.setAdapter(mAdapterEstadisticas);
            }});

    }

    private void buildPieChart(ArrayList<UsuarioCuestionario> progressUser){

        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i=0; i<progressUser.size(); i++){
            pieEntries.add(new PieEntry(progressUser.get(i).getCorrectAnswers(), progressUser.get(i).getQuizID()));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Avances");
        dataSet.setColors(COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = findViewById(R.id.pieChartEstadisticas);

        chart.setData(data);
        chart.animateY(1000);
        chart.getDescription().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.invalidate();
    }

    public static final int[] COLORS = {
            Color.rgb(255,255,0),
            Color.rgb(0,255,0),
            Color.rgb(255,0,0),
            Color.rgb(148, 0, 211),
            Color.rgb(0,0,225),
            Color.rgb(75,0,130),
            Color.rgb(255,127,0)
    };
}
