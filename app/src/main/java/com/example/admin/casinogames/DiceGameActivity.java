package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class DiceGameActivity extends Activity implements SensorEventListener {
    private Sensor acceleromter;
    private SensorManager sm;
    private ImageView dice1;
    private ImageView dice2;
    private TextView betMoney;
    private SeekBar seekBar;
    private Spinner spinner;
    private Button placeBetBtn;
    private Bundle bundle;
    private ArrayList userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_game_layout);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        userInfo = (ArrayList) bundle.get("userinfo");

        setViewOfFields();

        setButtonClickable();

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        acceleromter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean register = sm.registerListener(this,acceleromter,SensorManager.SENSOR_DELAY_NORMAL);
        if(!register) sm.unregisterListener(this);


    }

    private void setButtonClickable() {
        placeBetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bet = seekBar.getProgress();
                String sumChoise = spinner.getSelectedItem().toString();

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                betMoney.setText(progress+"$");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setViewOfFields() {
        dice1 = (ImageView) findViewById(R.id.die1);
        dice2 = (ImageView) findViewById(R.id.die2);
        betMoney = (TextView) findViewById(R.id.bet_money);
        seekBar = (SeekBar) findViewById(R.id.money_sk);
        seekBar.setMax((int)userInfo.get(4));
        spinner = (Spinner) findViewById(R.id.sum_spinner);
        placeBetBtn = (Button) findViewById(R.id.bet_btn);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
