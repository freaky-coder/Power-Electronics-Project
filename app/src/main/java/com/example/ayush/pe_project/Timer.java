package com.example.ayush.pe_project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Ayush on 08-12-2016.
 */
public class Timer extends Activity {

    Button back,timer_toggle;
    Text
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        back=(Button)findViewById(R.id.button_back);
        timer_toggle=(Button)findViewById(R.id.button_timer);
    }
}
