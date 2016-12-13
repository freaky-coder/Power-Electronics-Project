package com.example.ayush.pe_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ayush on 08-12-2016.
 */
public class Timer extends Activity {

    Button back,timer_toggle;
    NumberPicker hh1,mm1,ss1;
    int uuu=0;
    int prev_hh=0;
    int prev_mm=0;
    int prev_ss=0;
    final int finalUuu = uuu;
    long valuet=0;
    int seconds=0;
    int hh_int,mm_int,ss_int;
    String hour,min,sec;
   // SharedPreferences sharedPref=this.getSharedPreferences("LOL",MODE_PRIVATE);

    private TextView tvHour, tvMinute, tvSecond;
    private LinearLayout linearLayout1, linearLayout2;
    private Handler handler;
    private Runnable runnable;
    CountDownTimer cdt;
    MainActivity ma=new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttt_layout);
        back=(Button)findViewById(R.id.button_back);
        timer_toggle=(Button)findViewById(R.id.button_timer);
        timer_toggle.setText("Activate Timer!");
        timer_toggle.setEnabled(true);
        timer_toggle.setClickable(true);
        hh1=(NumberPicker) findViewById(R.id.hh);
        mm1=(NumberPicker) findViewById(R.id.mm);
        ss1=(NumberPicker) findViewById(R.id.ss);
        hh1.setEnabled(true);
        mm1.setEnabled(true);
        ss1.setEnabled(true);
        hh1.setMinValue(0);
        hh1.setMaxValue(999);
        hh1.setWrapSelectorWheel(false);
        mm1.setMinValue(0);
        mm1.setMaxValue(59);
        mm1.setWrapSelectorWheel(false);
        ss1.setMinValue(0);
        ss1.setMaxValue(59);
        ss1.setWrapSelectorWheel(false);
        initUI();


        timer_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer_toggle.getText()=="Activate Timer!"||timer_toggle.getText()=="ACTIVATE TIMER!") {
                    hh_int = hh1.getValue();
                    mm_int = mm1.getValue();
                    ss_int = ss1.getValue();
                    hh1.setEnabled(false);
                    mm1.setEnabled(false);
                    ss1.setEnabled(false);
                    timer_toggle.setText("Stop Timer!");

                    countDownStart();

                }
                else
                if(timer_toggle.getText()=="Stop Timer!")
                {
                    handler.removeCallbacks(runnable);
                   // Toast.makeText(getApplicationContext(),"Should print Start Timer",Toast.LENGTH_SHORT).show();
                    cdt.cancel();
                    prev_hh=Integer.parseInt(tvHour.getText().toString());
                    prev_mm=Integer.parseInt(tvMinute.getText().toString());
                    prev_ss=Integer.parseInt(tvSecond.getText().toString());
                    hh1.setValue(prev_hh);
                    mm1.setValue(prev_mm);
                    ss1.setValue(prev_ss);
                    timer_toggle.setText("Activate Timer!");
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // editor.putInt("LOL",'1');
              //  editor.commit();

                Intent i=new Intent(Timer.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    @SuppressLint("SimpleDateFormat")
    private void initUI() {

        linearLayout2 = (LinearLayout) findViewById(R.id.ll2);

        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);

    }
    public void countDownStart() {
        handler = new Handler();

        uuu=convert_sec(hh_int,mm_int,ss_int);
        System.out.println(uuu);
        runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    cdt=new CountDownTimer((uuu*1000),1000) {
                        @Override
                        public void onTick(long l) {
                            print_format( l);
                            if((l/1000)==0)
                            {
                            //    editor.putInt("LOL",1);
                                System.out.print("$$$$$$$$$$$$$$$$$$$$$$");
                            }
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(getApplicationContext(),"Timer End",Toast.LENGTH_SHORT).show();
                            hh1.setEnabled(true);
                            mm1.setEnabled(true);
                            ss1.setEnabled(true);
                            timer_toggle.setText("Activate Timer!");
                            hh1.setValue(0);
                            mm1.setValue(0);
                            ss1.setValue(0);
                            tvHour.setText(""+0);
                            tvMinute.setText(""+0);
                            tvSecond.setText(""+0);
                          //  sharedPref.edit().putInt("lola",1).commit();
                        }
                    }.start();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    // Take the timer values given in the test fields and convert it to seconds
    public int convert_sec(int hr,int min,int sec)
    {

        seconds=(hr*60*60)+(min*60)+sec;
        return seconds;
    }

    public void print_format(long l)
    {
        long second=l/1000;
        long hr=0,min=0,sec=0;
        hr=second/3600;
        min=(second%3600)/60;
        sec=second%60;
        tvHour.setText(""+hr);
        tvMinute.setText(""+min);
        tvSecond.setText(""+sec);
    }

}


