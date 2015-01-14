package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final String DEBUG = "Debug" ;

    private String userName;
    private String password;
    private Button singUP_btn;
    private Button singIn_btn;
    private EditText etUsername,etPassword;
    private TextView wrongInput;

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
        singIn_btn = (Button)findViewById(R.id.singIn_btn);
        singUP_btn = (Button)findViewById(R.id.singUp_btn);
        wrongInput = (TextView) findViewById(R.id.wrong);
    }
    private void setButtonClickable() {
        singIn_btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                userName = etUsername.getText().toString();
                password = etPassword.getText().toString();

                new loginCheck().execute(new apiConnectorDB());
            }
        });

        singUP_btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
    }

    private class loginCheck extends AsyncTask<apiConnectorDB,Long,JSONObject>{

        @Override
        protected JSONObject doInBackground(apiConnectorDB... params) {
            JSONArray jsonArray = params[0].getAllUsers();
            if(jsonArray != null){
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject json = null;
                    try{
                        json = jsonArray.getJSONObject(i);
                        if((json.getString("username").equals(userName) || json.getString("email").equals(userName)) && json.getString("password").equals(password)){
                            return json;
                        }

                    }catch (Exception e){
                        Log.e(DEBUG, "Faild getting the Json Object");
                    }
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            loginResult(jsonObject);
        }
    }

    private void loginResult(JSONObject jsonObject) {
        if(jsonObject != null){
            Intent intent = new Intent(this, CasinoLobbyActivity.class);
            //Create an Arraylist To pass The loged user info
            ArrayList userInfo = new ArrayList();
            try {
                userInfo.add(jsonObject.getInt("id"));
                userInfo.add(jsonObject.getString("username"));
                userInfo.add(jsonObject.getString("email"));
                userInfo.add(jsonObject.getString("password"));
                userInfo.add(jsonObject.getInt("totalmoney"));



            }catch (Exception e){
                Log.e(DEBUG,"Didnt parse thr Json to ArryList");
            }
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
