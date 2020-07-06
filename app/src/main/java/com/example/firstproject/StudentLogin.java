package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout lemail, lpassword;
    TextView textView;
    ProgressBar progressBar;
    Button loginbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        lemail = findViewById(R.id.loginemail);
        lpassword = findViewById(R.id.loginPassword);
        textView = findViewById(R.id.SignUp);
        progressBar = findViewById(R.id.progressBar2);
        loginbtn = findViewById(R.id.loginstudent);
        findViewById(R.id.SignUp).setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginstudent).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.SignUp:
             finish();
             startActivity(new Intent(this,StudentSignup.class));
           break;
         case R.id.loginstudent:
             userlogin();
             break;
     }

    }

    private void userlogin() {
        String email = lemail.getEditText().getText().toString().trim();
        String password = lpassword.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            lemail.setError("Email Is Required");
            return;
        } else if (TextUtils.isEmpty(password)) {
            lpassword.setError("Password Is Required");
            return;
        }
         progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()){
                finish();
                Toast.makeText(StudentLogin.this, "You Successfully Log in", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(StudentLogin.this,StudentVerification.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else {
            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
            }
        });

    }


    public void resetpwd(View view) {
      startActivity(new Intent(StudentLogin.this,ForgotPassword.class));
    }
}
