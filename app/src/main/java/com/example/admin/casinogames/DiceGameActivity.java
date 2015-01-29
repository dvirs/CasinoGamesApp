package com.example.admin.casinogames;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class DiceGameActivity extends Activity implements SensorEventListener {
    private Sensor acceleromter;
    private SensorManager sm;
    private ImageView dice1;
    private ImageView dice2;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_game_layout);

        title = (TextView) findViewById(R.id.dice_title_txt);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        acceleromter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean register = sm.registerListener(this,acceleromter,SensorManager.SENSOR_DELAY_NORMAL);
        if(!register) sm.unregisterListener(this);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
