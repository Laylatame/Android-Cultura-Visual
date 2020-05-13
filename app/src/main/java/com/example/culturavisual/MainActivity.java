package com.example.culturavisual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference usuariosReference;

    EditText editUsername;
    EditText editPassword;
    Button buttonIniciarSesion;
    TextView registerAccount;

    String username;
    String password;
    Boolean found = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);
        registerAccount = findViewById(R.id.textViewRegister);

        initializeFirebase();

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
                    //showMessages();
                }
            }
        });

        registerAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, RegistrarUsuario.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);

            }
        });


    }

    private void initializeFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        usuariosReference = firebaseDatabase.getReference("usuarios");
    }

    private void findUser(){
        usuariosReference.orderByChild("usuario").equalTo(username).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Usuarios userFound = dataSnapshot.getValue(Usuarios.class);

                if(userFound.getContrasena().equals(password)){
                    Intent myIntent = new Intent(MainActivity.this, mainScreen.class);
                    myIntent.putExtra("username", userFound.getUsuario());
                    MainActivity.this.startActivity(myIntent);

                } else{
                    Toast.makeText(getApplicationContext(),
                            "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

                found = true;

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private void showMessages(){
        if(found){
            Toast.makeText(getApplicationContext(),
                    "Encontró", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(),
                    "No encontró", Toast.LENGTH_SHORT).show();
        }
    }


}
