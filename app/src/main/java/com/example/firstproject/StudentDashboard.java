package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class StudentDashboard extends AppCompatActivity {
    TextView textView, textView1, textView2;
    ImageView imageView;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        imageView = findViewById(R.id.dashboardimage);
        textView = findViewById(R.id.nametext);
        textView1 = findViewById(R.id.gmailtext);

        fAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(fAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String studentemail = dataSnapshot.child("Email").getValue().toString();
                String studentname = dataSnapshot.child("FullName").getValue().toString();
                String image = dataSnapshot.child("ProfileImage").getValue().toString();
                textView.setText(studentname);
                textView1.setText(studentemail);
                    Glide.with(StudentDashboard.this).load(image).into(imageView);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void myprofile(View view) {
        startActivity(new Intent(this,StudentMyProfile.class));

    }

    public void searchfortutor(View view) { ;
    }
}
