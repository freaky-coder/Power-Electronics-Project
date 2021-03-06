package com.example.ayush.pe_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.widget.ToggleButton;
import java.io.IOException;
import java.util.UUID;


public class MainActivity extends Activity {

    Button b1, btnDis , tmr;
    SeekBar sb;
    TextView tv,info;
    int ttt=0;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    double firing_angle;
    double voltage_out;
    ToggleButton onoff;
    Handler handler;
//    SharedPreferences sharedPref=this.getSharedPreferences("LOL",MODE_PRIVATE);
    int prev_sb_value=0;
    int value=0;
    int i,x;
    static  final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("************LOL**********"+value+"\n");
        Intent newint = getIntent();
        address = newint.getStringExtra(BTDevicesList.EXTRA_ADDRESS);
        setContentView(R.layout.main_layout);
        final View v = findViewById(R.id.layout_id).getRootView();
        // v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        sb = (SeekBar) findViewById(R.id.seekBar);
        tv = (TextView) findViewById(R.id.textView2);
        final ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        tmr=(Button )findViewById(R.id.timer);

            new ConnectBT().execute(); //Call the class to connect

        btnDis = (Button) findViewById(R.id.button3);
        tv.setText("Current Set Value: " + "0");
        b1 = (Button) findViewById(R.id.button1);
        info = (TextView) findViewById(R.id.textView3);
        onoff = (ToggleButton) findViewById(R.id.toggleButton);
        b1.setText("More Info.");
        info.setText("  ");
        onoff.setClickable(true);
        onoff.setChecked(false);
        sb.setEnabled(false);


       handler = new Handler();

        final Runnable r = new Runnable() {
            public void run()
            {
                //ttt=sharedPref.getInt("lola",0);
                System.out.println("%%%%%%%%%%%%%%%%%%"+ttt+"%%%%%%%%%%%%%%%%%%%%%5");
                onoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onoff.isChecked() == false)
                            sb.setEnabled(true);
                        else {
                            try {
                                btSocket.getOutputStream().write(0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            sb.setEnabled(false);


                        }
                    }
                });
            }

        };
        handler.postDelayed(r, 1000);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(b1.getText()=="More Info.")
               {
                   b1.setText("Less Info.");
                               }
               else
               {
                   b1.setText("More Info.");
               }

            }
        });
        // Intent to the Timer Activity Page
        tmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Timer.class);
                startActivity(i);
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect(); //close connection
            }
        });
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int send_value = 0;

                    if (fromUser == true) {
                        firing_angle = (int)(180- (progress * 2.8125));
                        send_value = (int) (progress * 1.58730);
                       // voltage_out = (int) (230 * (Math.sqrt((Math.sin(2 * (firing_angle * 0.0174))) - (2 * (firing_angle * 0.0174)) + (2 * 3.14159)) / (2 * 3.14159)));
                        voltage_out=(int)(129.94*Math.sqrt((3.14-firing_angle)+(Math.sin(2*firing_angle))/2));
                        tv.setText("Intensity: " + send_value + "%");
                        if(b1.getText()=="Less Info.") {
                            info.setText("Firing Angle: " + firing_angle + "\u00b0");
                            info.append("\n");
                            info.append("Output Voltage(Vo): " + voltage_out + "V");
                        }
                        else if (b1.getText()=="More Info.")
                        {
                            info.setText(" ");
                        }


                       /* try {
                            btSocket.getOutputStream().write(progress );
                        } catch (IOException e) {

                        }*/
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    prev_sb_value=sb.getProgress();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    try {
                        btSocket.getOutputStream().write(sb.getProgress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            });

        }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }

    private void turnOff()
    {
        if (btSocket!=null)
        {
           /* try
            {
                //btSocket.getOutputStream().write(String.valueOf(progress).getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }*/
        }
    }

    private void turnOn()
    {
        if (btSocket!=null)
        {
           /* try
            {
                //btSocket.getOutputStream().write("TO".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }*/
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed.Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}
