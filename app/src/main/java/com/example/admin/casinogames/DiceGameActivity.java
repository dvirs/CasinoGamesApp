package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class DiceGameActivity extends Activity implements SensorEventListener {
    private static final int UPDATE_DELAY = 50;
    private static final int SHAKE_THRESHOLD = 800;
    private final int rollAnimations = 50;
    private final int delayTime = 15;

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

    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private boolean paused = false;
    private Resources res;
    private final int[] diceImages = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six};
    private Drawable dice[] = new Drawable[6];
    private final Random randomGen = new Random();
    private int diceSum;
    private int roll[] = new int[]{6, 6};
    private Handler animationHandler;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_game_layout);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        userInfo = (ArrayList) bundle.get("userinfo");

        setViewOfFields();
        setButtonClickable();
        res = getResources();
        for (int i = 0; i < 6; i++) {
            dice[i] = res.getDrawable(diceImages[i]);
        }
        animationHandler = new Handler() {
            public void handleMessage(Message msg) {
                dice1.setImageDrawable(dice[roll[0]]);
                dice2.setImageDrawable(dice[roll[1]]);
            }
        };

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        acceleromter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean resultRegister = sm.registerListener(this,acceleromter,SensorManager.SENSOR_DELAY_NORMAL);
        if(!resultRegister) sm.unregisterListener(this); //NO accelerometer on this device


    }

    private void rollDice() {
        if (paused) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < rollAnimations; i++) {
                    doRoll();
                }
            }
        }).start();
        MediaPlayer mp = MediaPlayer.create(DiceGameActivity.this ,R.raw.roll);
        try {
            mp.prepare();
        }catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    private void doRoll() {
        roll[0] = randomGen.nextInt(6);
        roll[1] = randomGen.nextInt(6);
        diceSum = roll[0] + roll[1] + 2; // 2 is added because the values of the rolls start with 0 not 1
        synchronized (getLayoutInflater()) {
            animationHandler.sendEmptyMessage(0);
        }
        try { // delay to alloy for smooth animation
            Thread.sleep(delayTime);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setButtonClickable() {
        placeBetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bet = seekBar.getProgress();
                String sumChoise = spinner.getSelectedItem().toString();
                if((bet != 0)) {
                //play sensor


                }
                else {
                    Toast.makeText(DiceGameActivity.this,R.string.bet_error,Toast.LENGTH_SHORT).show();

                }

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
        Log.e("ddddddddddd","the pphone moved");
        Sensor mySensor = event.sensor;
        long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > UPDATE_DELAY) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                Log.e("ddddddddddd",speed +" - "+ SHAKE_THRESHOLD);
                if (speed > SHAKE_THRESHOLD) { //the screen was shaked
                    rollDice();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }
}
