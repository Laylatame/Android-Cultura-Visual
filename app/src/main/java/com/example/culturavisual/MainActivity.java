package com.example.culturavisual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Login screen

    EditText editUsername;
    EditText editPassword;
    Button buttonIniciarSesion;
    TextView registerAccount;

    String username;
    String password;

    FirebaseFirestore db;
    CollectionReference usersCollection;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        registerAccount = findViewById(R.id.textViewRegister);


        db = FirebaseFirestore.getInstance();
        usersCollection = db.collection("usuarios");

    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                username = editUsername.getText().toString();
                password = editPassword.getText().toString();

                if(username.equals("")){
                    validation();
                } else if (password.equals("")){
                    validation();
                } else{
                    findUser();
                }
            }
        });

        registerAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, RegistrarUsuario.class);
                MainActivity.this.startActivity(myIntent);

            }
        });

    }

    private void findUser(){

        DocumentReference userRef = usersCollection.document(username);

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String dbUsername = documentSnapshot.getString("usuario");
                    String dbPassword = documentSnapshot.getString("contrasena");

                    Usuarios userFound = new Usuarios(dbUsername, dbPassword);


                    if(userFound.getContrasena().equals(password)){
                        Intent myIntent = new Intent(MainActivity.this, mainScreen.class);
                        myIntent.putExtra("user", userFound);
                        MainActivity.this.startActivity(myIntent);

                    } else{
                        Toast.makeText(getApplicationContext(),
                                "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "El usuario no existe, porfavor regístrate.", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void validation(){
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();


        if(username.equals("")){
            editUsername.setError("Required");
        } else if(password.equals("")){
            editPassword.setError("Required");
        }
    }



}
