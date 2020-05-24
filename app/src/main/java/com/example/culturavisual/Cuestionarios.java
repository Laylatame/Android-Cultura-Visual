package com.example.culturavisual;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class Cuestionarios extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterCuestionario mAdapterCuestionario;
    private ArrayList<CuestionarioObj> mCuestionariosList;
    private ArrayList<UsuarioCuestionario> mProgressUser;
    Usuarios loggedUser;

    FirebaseFirestore db;
    CollectionReference quizesCollection;
    CollectionReference usuarioQuiz;
    DocumentReference userQuizRef;
    String dbCuestionarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios);

        db = FirebaseFirestore.getInstance();
        quizesCollection = db.collection("quizes");
        usuarioQuiz = db.collection("usuarioQuiz");

        mRecyclerView = findViewById(R.id.recyclerViewCuestionarios);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loggedUser = (Usuarios)getIntent().getSerializableExtra("user");


        mCuestionariosList = new ArrayList<>();
        mProgressUser = new ArrayList<>();

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
                                dbCuestionarioID = document.getString("quizID");
                                mCuestionariosList.add(new CuestionarioObj(dbNombreCuestionario, dbImagenURL, dbCuestionarioID));
                                userQuizRef = usuarioQuiz.document(loggedUser.getUsuario()+dbCuestionarioID);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No hay cuestionarios disponibles.", Toast.LENGTH_SHORT).show();
                        }

                        readData(new FirestoreCallback() {
                            @Override
                            public void onCallback(ArrayList<UsuarioCuestionario> list) {
                                mAdapterCuestionario = new AdapterCuestionario(Cuestionarios.this, mCuestionariosList, loggedUser, mProgressUser);
                                mRecyclerView.setAdapter(mAdapterCuestionario);
                            }
                        });
                    }
                });
    }

    private void readData(final FirestoreCallback firestoreCallback) {
        // Create a query against the collection.
        Query query = usuarioQuiz.whereEqualTo("user", loggedUser.getUsuario());

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<DocumentSnapshot> snapshotsList = documentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotsList) {
                    String dbUser = snapshot.getString("user");
                    String dbQuizID = snapshot.getString("quizID");
                    int dbCorrectAnswers = Integer.valueOf(snapshot.getString("correctAnswers"));
                    int dbWrongAnswers = Integer.valueOf(snapshot.getString("wrongAnswers"));
                    int dbScore = Integer.valueOf(snapshot.getString("score"));
                    String dbQuizName = snapshot.getString("quizName");
                    String dbQuizImage = snapshot.getString("quizImage");

                    UsuarioCuestionario userQuiz = new UsuarioCuestionario(dbUser, dbQuizID, Integer.valueOf(dbCorrectAnswers), Integer.valueOf(dbWrongAnswers), Integer.valueOf(dbScore), dbQuizName, dbQuizImage);
                    mProgressUser.add(userQuiz);
                }

                firestoreCallback.onCallback(mProgressUser);
            }});
    }

    private interface FirestoreCallback{
        void onCallback(ArrayList<UsuarioCuestionario> list);
    }
}
