package com.example.firstproject;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class TutorSignUp extends AppCompatActivity {
  TextInputLayout tgender,temail,tphonenumber,tpassword;
  Button signupbtn;
  TextView textView;
  FirebaseAuth fAuth;
  ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_sign_up);
        tgender= findViewById(R.id.tutorgender);
        temail= findViewById(R.id.tutoremailaddress);
        tphonenumber= findViewById(R.id.tutorphonenumber);
        tpassword= findViewById(R.id.tutorpassword);
        fAuth = FirebaseAuth.getInstance();
        signupbtn = findViewById(R.id.TutorRegister);
        progressBar = findViewById(R.id.progressbar4);
        textView = findViewById(R.id.login);

        signupbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String email = temail.getEditText().getText().toString().trim();
                String password = tpassword.getEditText().getText().toString().trim();
                final String phonenumber = tphonenumber.getEditText().getText().toString().trim();
                final String Gender = tgender.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    temail.setError("Email Is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    tpassword.setError("Password Is Required");
                    return;
                }
                if (password.length() < 6) {
                    tpassword.setError("Password must be greater than 6 letters");
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //send verification email
                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(TutorSignUp.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                            DBheplerTutor tutor = new DBheplerTutor( email, Gender, phonenumber);

                            FirebaseDatabase.getInstance().getReference("Tutor").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(tutor).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(TutorSignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), TutorLogin.class));
                                    finish();

                                }
                            });
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "You are Already Register",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorSignUp.this,TutorLogin.class);
                startActivity(intent);
            }
        });
    }
}
