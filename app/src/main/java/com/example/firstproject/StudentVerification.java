package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class StudentVerification extends AppCompatActivity {
    Button ResendEmail, NextButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_verification);

        ResendEmail = findViewById(R.id.verificationemail);
        NextButton = findViewById(R.id.nextbutton);
        progressBar = findViewById(R.id.ver_progress);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        ResendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StudentVerification.this, "Verification Email Has Been Sent....Check Your Inbox", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        NextButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              progressBar.setVisibility(View.VISIBLE);
                                             if (!user.isEmailVerified()){
                                                 Toast.makeText(StudentVerification.this, "You Have To Verify The Email Address For Futher Process", Toast.LENGTH_SHORT).show();
                                                 progressBar.setVisibility(View.GONE);
                                             }
                                             if (user.isEmailVerified()){
                                                progressBar.setVisibility(View.VISIBLE);
                                                 Toast.makeText(StudentVerification.this, "Welcome to Dashboard", Toast.LENGTH_SHORT).show();
                                              Intent intent= new Intent(StudentVerification.this,StudentDashboard.class);
                                              startActivity(intent);
                                              finish();
                                              progressBar.setVisibility(View.GONE);
                                             }
                                          }
                                      }
        );
    }

}