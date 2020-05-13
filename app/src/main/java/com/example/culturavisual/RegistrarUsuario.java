package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrarUsuario extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference usuariosReference;


    EditText registerUsername;
    EditText registerContrasena;
    Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        registerUsername = findViewById(R.id.registerUsername);
        registerContrasena = findViewById(R.id.registerPassword);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        initializeFirebase();

        buttonRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        usuariosReference = firebaseDatabase.getReference("usuarios");

    }

    private void createUser(){
        final String username = registerUsername.getText().toString();
        String password = registerContrasena.getText().toString();

        if(username.equals("")){
            validation();
        } else if (password.equals("")){
            validation();
        } else{
            Usuarios user = new Usuarios();
            user.setUsuario(username);
            user.setContrasena(password);
            databaseReference.child("usuarios").child(user.getUsuario()).setValue(user);

            Toast.makeText(getApplicationContext(),
                    "Agregado", Toast.LENGTH_SHORT).show();
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

