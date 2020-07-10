package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentMyProfile extends AppCompatActivity {
    TextInputLayout sclass, saddress, sphonenumber, sgender,semail, sname;
    Button updatebtn;
    ImageView imageView;
    FirebaseAuth fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_my_profile);
        semail = findViewById(R.id.myprofileemailaddress);
        sname = findViewById(R.id.myprofilename);
        sclass = findViewById(R.id.myprofileclass);
        saddress = findViewById(R.id.myprofileaddress);
        sphonenumber = findViewById(R.id.myprofilephonenumber);
        imageView = findViewById(R.id.myprofilepic);
        sgender = findViewById(R.id.myprofilegender);
        updatebtn = findViewById(R.id.updatebutton);
        fauth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(fauth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("FullName").getValue().toString();
                String studentemail = dataSnapshot.child("Email").getValue().toString();
                String studentclass = dataSnapshot.child("StudentClass").getValue().toString();
                String studentaddress = dataSnapshot.child("Address").getValue().toString();
                String phno= dataSnapshot.child("Phonenumber").getValue().toString();
                String studentgender= dataSnapshot.child("Gender").getValue().toString();

                sname.getEditText().setText(name);
                semail.getEditText().setText(studentemail);
                sclass.getEditText().setText(studentclass);
                saddress.getEditText().setText(studentaddress);
                sphonenumber.getEditText().setText(phno);
                sgender.getEditText().setText(studentgender);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
