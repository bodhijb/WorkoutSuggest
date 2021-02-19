package com.jf2mc1.a015004rexerciseideas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jf2mc1.a015004rexerciseideas.R;

public class Splash extends AppCompatActivity {

    private boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!splashLoaded) {
            setContentView(R.layout.activity_splash);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 1000);
            splashLoaded = true;
        } else {
            // screen has been loaded previously so..
            Intent goStraightToMainActivity = new Intent(Splash.this, MainActivity.class);
            goStraightToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goStraightToMainActivity);
            finish();
        }
    }
}