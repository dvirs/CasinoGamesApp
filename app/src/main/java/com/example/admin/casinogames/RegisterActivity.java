package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.EncryptedPrivateKeyInfo;

public class RegisterActivity extends Activity {
    private static final String REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
    private final int MIN_LEN = 6;

    private EditText password,email,rePassword,userName;
    private TextView userNameTxt, passwordTxt, rePasswordTxt, wrongInputTxt, emailTxt;
    private Button signUp;
    private String user,firstPass,secondPass,emailStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setViewOfFields();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = ""+userName.getText().toString();
                emailStr = ""+email.getText().toString();
                firstPass = ""+password.getText().toString();
                secondPass = ""+rePassword.getText().toString();

                    if(validations(emailStr, firstPass, secondPass)){
                    ArrayList<NameValuePair> userInfoArray = new ArrayList<NameValuePair>();
                    userInfoArray.add(new BasicNameValuePair("username",user));
                    userInfoArray.add(new BasicNameValuePair("email",emailStr));
                    userInfoArray.add(new BasicNameValuePair("password",firstPass));
                    new insertUserTask(userInfoArray).execute(new apiConnectorDB());

                } else {
                    wrongInputTxt.setText(R.string.wrong_reg);
                }
            }
        });
    }

    private class insertUserTask extends AsyncTask<apiConnectorDB,Long,Boolean>{
        private ArrayList<NameValuePair> userInfoArray;
        private insertUserTask(ArrayList<NameValuePair> userInfoArray) {
            this.userInfoArray = userInfoArray;
        }

        @Override
        protected Boolean doInBackground(apiConnectorDB... params) {
            JSONArray jsonArray = params[0].getAllUsers();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = null;
                    try {
                        json = jsonArray.getJSONObject(i);
                        if (json.getString("username").equals(userInfoArray.get(0).getValue()) ||
                                json.getString("email").equals(userInfoArray.get(1).getValue())) {
                            return false;
                        }
                    } catch (Exception e) {
                        Log.e("de", "Faild getting the Json Object");
                    }
                }
            }
            return params[0].InsertUser(userInfoArray);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(aBoolean){
                signInSuccessful();
            }else{
                signInNotSuccessful();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    private void signInNotSuccessful() {
        //Not successful sign in
        wrongInputTxt.setText(R.string.user_name_is_taken);
    }

    private void signInSuccessful() {
        //successful Sign in, move to next activity
        Toast.makeText(this,R.string.sign_successful,Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    private void setViewOfFields() {
        signUp = (Button) findViewById(R.id.singUp_btn_reg);
        email = (EditText) findViewById(R.id.email_et);
        password = (EditText) findViewById(R.id.password_et_reg);
        rePassword = (EditText) findViewById(R.id.rePassword_et_reg);
        userName = (EditText) findViewById(R.id.userName_reg);
        emailTxt = (TextView) findViewById(R.id.email_reg);
        userNameTxt = (TextView) findViewById(R.id.userName_txt_reg);
        passwordTxt = (TextView) findViewById(R.id.password_reg);
        rePasswordTxt = (TextView) findViewById(R.id.rePassword_reg);
        wrongInputTxt = (TextView) findViewById(R.id.wrong_reg);
    }

    private boolean validations(String email, String password, String rePassword) {
        boolean flag = false;
        if(isValidEmail(email)) {
            flag = true;
        }else { flag = false;}
        if(isValidPassword(password,rePassword)){
            flag = true;
        }else { flag = false;}
        return flag;
    }

    private boolean isValidEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            emailTxt.setTextColor(Color.WHITE);
            return true;
        }
        else {
            email.setText("");
            password.setText("");
            rePassword.setText("");
            emailTxt.setTextColor(Color.RED);
            return false;
        }
    }

    private boolean isValidPassword(String password, String rePassword) {
        if(!password.equals(rePassword) || password.equals("")) {
            wrongPassInput();
            return false;
        }else if(!password.matches(REGEX)){
            wrongPassInput();
            return false;
        }
        return true;
    }

    private void wrongPassInput() {
        this.password.setText("");
        this.rePassword.setText("");
        passwordTxt.setTextColor(Color.RED);
        rePasswordTxt.setTextColor(Color.RED);
    }

}
