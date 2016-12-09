package com.example.ayush.pe_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Ayush on 09-12-2016.
 */
public class FirstPage extends Activity {
    ProgressBar pb;
    private static int SPLASH_TIME_OUT = 3000;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        pb=(ProgressBar)findViewById(R.id.progressBar2);
        new Handler().postDelayed(new Runnable() {
             @Override
            public void run() {
                 Toast.makeText(FirstPage.this,"Initializing... Please Wait!",Toast.LENGTH_SHORT);
                 Intent i = new Intent(FirstPage.this, BTDevicesList.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
