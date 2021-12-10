package com.example.realestatesearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestatesearchapp.tree.NullKeyException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStreamReader;

public class Register extends AppCompatActivity {
    EditText f_Name, e_mail, pwd;
    Button register, back;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        f_Name = findViewById(R.id.editTextTextPersonName);
        e_mail = findViewById(R.id.editTextTextEmailAddress);
        pwd = findViewById(R.id.editTextTextPassword2);

        register = findViewById(R.id.button);
        back = findViewById(R.id.button2);

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = e_mail.getText().toString().trim();
                String password = pwd.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    e_mail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    pwd.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    pwd.setError("password should be more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else {
                            Toast.makeText(Register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    public void login(View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}