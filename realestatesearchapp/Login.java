package com.example.realestatesearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class Login extends AppCompatActivity {

    RelativeLayout relay1;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relay1.setVisibility(View.VISIBLE);

        }
    };

    EditText e_mail, pwd;
    Button login, register;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relay1 = findViewById(R.id.login);
        handler.postDelayed(runnable, 1500);

        e_mail = findViewById(R.id.editTextTextEmailAddress);
        pwd = findViewById(R.id.editTextTextPassword2);


        register = findViewById(R.id.register_bt);
        login = findViewById(R.id.login_bt);

        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*try {
                    DataManager.search();
                } catch (UnrecognizedTokenException e) {
                    e.printStackTrace();
                } catch (UnexpectedTokenException e) {
                    e.printStackTrace();
                } catch (NullKeyException e) {
                    e.printStackTrace();
                }*/
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

                //progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

    public void register(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }
}