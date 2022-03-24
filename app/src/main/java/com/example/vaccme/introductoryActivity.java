package com.example.vaccme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class introductoryActivity extends AppCompatActivity {
    ImageView appName;
    LottieAnimationView lottieAnimationView;
    Context context = introductoryActivity.this;
    final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        appName = findViewById(R.id.app_name);

        //maor - change
        new Handler().postDelayed(new Runnable() {

            /**
             * Showing splash screen with a timer. This will be useful when you
             * want to showcase your app logo or company logo
             */

            @Override
            public void run() {
                appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
                Helper.changeActivityAndFinish(context, LoginScreen.class);
            }
        }, SPLASH_TIME_OUT);

    }
}