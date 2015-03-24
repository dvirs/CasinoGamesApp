package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class SettingActivity extends Activity {

    private static final String PREFS_NAME = "com.example.admin.memorygame.prefs";
    private static final String PREFS_BGMUSICON_KEY = "bmusicon";


    SharedPreferences prefs;
    SharedPreferences.Editor prefsWriter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        prefs= this.getSharedPreferences(PREFS_NAME, 0);
        prefsWriter = prefs.edit();

        ((CheckBox)this.findViewById(R.id.config_bgmusic_enable)).setOnClickListener(bgMusicListener);
        ((CheckBox)this.findViewById(R.id.config_bgmusic_enable)).setChecked(getBgMusicEnabled(this));

    }


    View.OnClickListener bgMusicListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            prefsWriter.putBoolean(PREFS_BGMUSICON_KEY, ((CheckBox)v).isChecked());
            prefsWriter.commit();
        }

    };

    public static boolean getBgMusicEnabled(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(PREFS_BGMUSICON_KEY, true);
    }

}

