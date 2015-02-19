package com.example.admin.casinogames;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import com.example.admin.casinogames.com.example.admin.tasks.InsertUserTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class RegisterActivity extends Activity {
    private static final String REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
    private final int MIN_LEN = 6;

    private EditText password,email,rePassword,userName;
    private TextView userNameTxt, passwordTxt, rePasswordTxt, wrongInputTxt, emailTxt;
    private Button signUp;
    private String user,firstPass,secondPass,emailStr;
    private RegisterActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setViewOfFields();
        setButtonClickListener();

    }

    private void setButtonClickListener() {
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
                    //Create a new Insert User Task
                    new InsertUserTask(userInfoArray,activity).execute(new apiConnectorDB());

                } else {
                    wrongInputTxt.setText(R.string.wrong_reg);
                }
            }
        });
    }


    public void signInNotSuccessful() {
        //Not successful sign in
        wrongInputTxt.setText(R.string.user_name_is_taken);
    }

    public void signInSuccessful() {
        //successful Sign in, move to next activity
        Toast.makeText(this,R.string.sign_successful,Toast.LENGTH_LONG).show();
        ActivityCompat.finishAffinity(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }

    private void setViewOfFields() {
        signUp = (Button) findViewById(R.id.sginUp_btn_reg);
        email = (EditText) findViewById(R.id.email_et);
        password = (EditText) findViewById(R.id.password_et_reg);
        rePassword = (EditText) findViewById(R.id.rePassword_et_reg);
        userName = (EditText) findViewById(R.id.userName_reg);
        emailTxt = (TextView) findViewById(R.id.email_reg);
        userNameTxt = (TextView) findViewById(R.id.userName_txt_reg);
        passwordTxt = (TextView) findViewById(R.id.password_reg);
        rePasswordTxt = (TextView) findViewById(R.id.rePassword_reg);
        wrongInputTxt = (TextView) findViewById(R.id.wrong_reg);
        activity = this;
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


}
