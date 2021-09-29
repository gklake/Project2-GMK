package com.example.project2_gmk;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#6F7F8E"))
                .withBeforeLogoText("Gabby's Mech Keebs")
                .withLogo(R.mipmap.ic_launcher_custom_round)
                .withFooterText("Established Spring 2021");


        myCustomTextViewAnimation(config.getBeforeLogoTextView());

        //Change text color
        config.getBeforeLogoTextView().setTextColor(Color.parseColor("#332847"));
        config.getFooterTextView().setTextColor(Color.parseColor("#332847"));

        //Creating the view
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation = new TranslateAnimation(0,0,-1000,0);
        animation.setDuration(2500);
        tv.startAnimation(animation);
    }

}