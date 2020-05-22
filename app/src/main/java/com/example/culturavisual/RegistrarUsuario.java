package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrarUsuario extends AppCompatActivity {

    FirebaseFirestore db;
    CollectionReference usersCollection;

    EditText registerUsername;
    EditText registerContrasena;
    Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        db = FirebaseFirestore.getInstance();
        usersCollection = db.collection("usuarios");

        registerUsername = findViewById(R.id.registerUsername);
        registerContrasena = findViewById(R.id.registerPassword);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);


        buttonRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser(){
        final String username = registerUsername.getText().toString();
        final String password = registerContrasena.getText().toString();

        if(username.equals("")){
            validation();
        } else if (password.equals("")){
            validation();
        } else{
            final Usuarios user = new Usuarios();
            user.setUsuario(username);
            user.setContrasena(password);

            DocumentReference userRef = usersCollection.document(username);

            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Toast.makeText(getApplicationContext(),
                                "Ese usuario ya existe, porfavor elige otro", Toast.LENGTH_SHORT).show();
                    } else{
                        usersCollection.document(username).set(user);

                        Toast.makeText(getApplicationContext(),
                                "Usuario creado.", Toast.LENGTH_SHORT).show();

                        Intent myIntent = new Intent(RegistrarUsuario.this, mainScreen.class);
                        myIntent.putExtra("user", user);
                        RegistrarUsuario.this.startActivity(myIntent);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private void validation(){
        String username = registerUsername.getText().toString();
        String password = registerContrasena.getText().toString();

        if(username.equals("")){
            registerUsername.setError("Required");
        } else if(password.equals("")){
            registerContrasena.setError("Required");
        }
    }
}

