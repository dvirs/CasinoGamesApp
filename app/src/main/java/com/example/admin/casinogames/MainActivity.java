package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import com.example.admin.casinogames.com.example.admin.tasks.loginCheckTask;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final String DEBUG = "Debug" ;

    private String userName;
    private String password;
    private Button signIn_btn;
    private EditText etUsername,etPassword;
    private TextView wrongInput;
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewOfFields();
        setButtonClickable();

    }



    private void setViewOfFields() {
        etUsername = (EditText) findViewById(R.id.userName);
        etPassword = (EditText) findViewById(R.id.password);
        signIn_btn = (Button)findViewById(R.id.signIn_btn_log);
        wrongInput = (TextView) findViewById(R.id.wrong);
        activity = this;
    }
    private void setButtonClickable() {
        signIn_btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                userName = etUsername.getText().toString();
                password = etPassword.getText().toString();

                new loginCheckTask(userName,password,activity).execute(new apiConnectorDB());

            }
        });
    }

    public void loginResult(ArrayList<String> userInfo) {
        if(userInfo != null){
            Intent intent = new Intent(this, CasinoLobbyActivity.class);
            intent.putExtra("userinfo",userInfo);
            startActivity(intent);
        }else{
            etPassword.setText("");
            wrongInput.setText(R.string.wrongInput);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
