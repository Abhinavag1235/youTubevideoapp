package com.app.toyo.youtubevideosapp;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.toyo.youtubevideosapp.R;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent=new Intent(MainActivity.this,Fox.class);
                startActivity(homeintent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }



}
