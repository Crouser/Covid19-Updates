package com.ashrafmahmood.safelucknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run(){
                Intent splash = new Intent(Splash.this, CasesInIndia.class);
                startActivity(splash);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
