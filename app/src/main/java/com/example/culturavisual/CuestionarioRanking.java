package com.example.culturavisual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuestionarioRanking extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterRanking mAdapterRanking;
    private ArrayList<UsuarioCuestionario> mRankingList;
    private Usuarios loggedUser;
    private CuestionarioObj cuestionario;


    private TextView tituloCuestionario;
    private ImageView imagenCuestionario;
    private Button botonIniciar;
    private Button botonRegresarCuestionarios;

    FirebaseFirestore db;
    CollectionReference rankingCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario_ranking);

        db = FirebaseFirestore.getInstance();
        rankingCollection = db.collection("usuarioQuiz");

        tituloCuestionario = findViewById(R.id.textViewCuestionario);
        imagenCuestionario = findViewById(R.id.imageViewRanking);
        botonIniciar = findViewById(R.id.botonIniciarCuestionario);
        botonRegresarCuestionarios = findViewById(R.id.botonRegresarCuestionarios);

        mRecyclerView = findViewById(R.id.recyclerViewRanking);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");
        cuestionario = (CuestionarioObj)getIntent().getSerializableExtra("quiz");

        tituloCuestionario.setText(cuestionario.getNombreCuestionario());
        Picasso.with(CuestionarioRanking.this).load(cuestionario.getImagenURL()).fit().centerInside().into(imagenCuestionario);

        mRankingList = new ArrayList<>();

        getRanking();

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(CuestionarioRanking.this, Preguntas.class);
                myIntent.putExtra("user", loggedUser);
                myIntent.putExtra("quiz", cuestionario);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CuestionarioRanking.this.startActivity(myIntent);
            }
        });

        botonRegresarCuestionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(CuestionarioRanking.this, Cuestionarios.class);
                myIntent.putExtra("user", loggedUser);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CuestionarioRanking.this.startActivity(myIntent);

            }
        });
    }

    public void getRanking(){

        // Create a query against the collection.
        Query query = rankingCollection.whereEqualTo("quizID", cuestionario.getCuestionarioID());

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
                    String dbQuizName = snapshot.getString("quizName");
                    String dbQuizImage = snapshot.getString("quizImage");

                    UsuarioCuestionario current = new UsuarioCuestionario(dbUsuario, dbQuizID, dbCorrectas, dbIncorrectas, dbScore, dbQuizName, dbQuizImage);
                    mRankingList.add(current);
                }


                Collections.sort(mRankingList);
                mAdapterRanking = new AdapterRanking(CuestionarioRanking.this, mRankingList);
                mRecyclerView.setAdapter(mAdapterRanking);
            }});
    }
}
