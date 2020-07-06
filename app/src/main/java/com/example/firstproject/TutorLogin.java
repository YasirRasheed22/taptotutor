package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TutorLogin extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout temail, tpassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);
        temail = findViewById(R.id.Tutorloginemail);
        tpassword = findViewById(R.id.TutorloginPassword);
        findViewById(R.id.TutorSignup).setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        findViewById(R.id.logintutor).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TutorSignup:
                finish();
                startActivity(new Intent(this, TutorSignUp.class));
                break;
            case R.id.logintutor:
                tutorlogin();
                break;
        }

    }

    private void tutorlogin() {

        String email = temail.getEditText().getText().toString().trim();
        String password = tpassword.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            temail.setError("Email Is Required");
            return;
        } else if (TextUtils.isEmpty(password)) {
            tpassword.setError("Password Is Required");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(TutorLogin.this, "You Successfully Log in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TutorLogin.this, TutorVerfication.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

