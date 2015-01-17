package com.example.admin.casinogames;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class CasinoLobbyActivity extends Activity {

    private Bundle bund;
    private TextView userNameTxt, totalMoneyTxt;
    private ImageButton cubes_btn, poker_btn;
    private Button settingsBtn, highScoreBtn, logoutBtn, signInBtn, signUpBtn;
    private ArrayList userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_lobby);

        setViewOfFields();
        Intent intent = getIntent();
        bund = intent.getExtras();
        if(bund == null) {
            cubes_btn.setVisibility(View.INVISIBLE);
            poker_btn.setVisibility(View.INVISIBLE);
            settingsBtn.setVisibility(View.INVISIBLE);
            highScoreBtn.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
        }
        else {
            cubes_btn.setVisibility(View.VISIBLE);
            poker_btn.setVisibility(View.VISIBLE);
            settingsBtn.setVisibility(View.VISIBLE);
            highScoreBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.VISIBLE);
            signInBtn.setVisibility(View.INVISIBLE);
            signUpBtn.setVisibility(View.INVISIBLE);

            userInfo = (ArrayList) bund.get("userinfo");
            userNameTxt.setText("Hello, " + userInfo.get(1).toString());
            totalMoneyTxt.setText(userInfo.get(4).toString() + "$");

        }

        setButtonClickable();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
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

    private void setViewOfFields() {
        userNameTxt = (TextView) findViewById(R.id.hi_txt);
        totalMoneyTxt = (TextView) findViewById(R.id.total_money_txt);
        cubes_btn = (ImageButton) findViewById(R.id.cubes_btn);
        poker_btn = (ImageButton) findViewById(R.id.poker_btn);
        settingsBtn = (Button) findViewById(R.id.settings_btn);
        highScoreBtn = (Button) findViewById(R.id.high_score_btn);
        logoutBtn = (Button) findViewById(R.id.logout_btn);
        signInBtn = (Button) findViewById(R.id.sign_in_btn_lob);
        signUpBtn = (Button) findViewById(R.id.sign_up_btn_lob);
    }

    private void setButtonClickable() {
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CasinoLobbyActivity.this, MainActivity.class);
                CasinoLobbyActivity.this.startActivity(i);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CasinoLobbyActivity.this, RegisterActivity.class);
                CasinoLobbyActivity.this.startActivity(i);
            }
        });
    }

}
