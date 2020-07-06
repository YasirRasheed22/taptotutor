package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    //Animation
    Animation topamin,bottomamin;
    TextView viewtext;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topamin = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomamin = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        image=findViewById(R.id.Splash);
        viewtext = findViewById(R.id.slogan);
        image.setAnimation(topamin);
        viewtext.setAnimation(bottomamin);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,SelectProfession.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }

}
