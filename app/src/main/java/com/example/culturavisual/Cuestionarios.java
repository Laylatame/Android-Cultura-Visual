package com.example.culturavisual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;

public class Cuestionarios extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterCuestionario mAdapterCuestionario;
    private ArrayList<CuestionarioObj> mCuestionariosList;
    Usuarios loggedUser;

    FirebaseFirestore db;
    CollectionReference quizesCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios);

        db = FirebaseFirestore.getInstance();
        quizesCollection = db.collection("quizes");

        mRecyclerView = findViewById(R.id.recyclerViewCuestionarios);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");


        mCuestionariosList = new ArrayList<>();

        getCuestionariosFromDB();

    }

    public void getCuestionariosFromDB(){

        quizesCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String dbNombreCuestionario = document.getString("quizName");
                                String dbImagenURL = document.getString("quizImage");
                                String dbCuestionarioID = document.getString("quizID");

                                mCuestionariosList.add(new CuestionarioObj(dbNombreCuestionario, dbImagenURL, dbCuestionarioID));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No hay cuestionarios", Toast.LENGTH_SHORT).show();
                        }
                        mAdapterCuestionario = new AdapterCuestionario(Cuestionarios.this, mCuestionariosList, loggedUser);
                        mRecyclerView.setAdapter(mAdapterCuestionario);

                    }
                });
    }
}
