package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class CasinoLobbyActivity extends Activity {

    private Bundle bund;
    private TextView userName_txt;
    private TextView totalMoney_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userName_txt = (TextView) findViewById(R.id.hi_txt);
        totalMoney_txt = (TextView) findViewById(R.id.total_money_txt);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_lobby);
        Intent intent = getIntent();
        bund = intent.getExtras();
        ArrayList userInfo = new ArrayList();
        userInfo = (ArrayList) bund.get("userinfo");


        Log.e("debug", "len = " + userInfo.size());
        Log.e("debug", "0 = " +  userInfo.get(0).toString());
        Log.e("debug", "1 = " +  userInfo.get(1).toString());
        Log.e("debug", "2 = " +  userInfo.get(2).toString());
        Log.e("debug", "3 = " +  userInfo.get(3).toString());
        Log.e("debug", "4 = " +  userInfo.get(4).toString());
        //userName_txt.setText("Hi " + userInfo.get(1));
        //totalMoney_txt.setText("" + userInfo.get(4).toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_casino_lobby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
