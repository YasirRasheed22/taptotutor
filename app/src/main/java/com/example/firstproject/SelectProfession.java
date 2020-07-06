package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectProfession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button StudentButton,b2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profession);
        StudentButton= findViewById(R.id.studentbutton);
        b2 = findViewById(R.id.tutorbutton);
        StudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectProfession.this,StudentLogin.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectProfession.this,TutorLogin.class);
                startActivity(intent);
            }
        });
    }
}
