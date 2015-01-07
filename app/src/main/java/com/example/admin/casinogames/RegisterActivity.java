package com.example.admin.casinogames;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private final int MIN_LEN = 6;

    private EditText firstPassword;
    private EditText secondPassword;
    private EditText userName;
    private EditText email;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUp = (Button) findViewById(R.id.singUp_btn_reg);
        email = (EditText) findViewById(R.id.email_et);
        firstPassword = (EditText) findViewById(R.id.password_et_reg);
        secondPassword = (EditText) findViewById(R.id.rePassword_et_reg);
        userName = (EditText) findViewById(R.id.userName_reg);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                String user = userName.getText().toString();
                String emailStr = email.getText().toString();
                String firstPass = firstPassword.getText().toString();
                String secondPass = secondPassword.getText().toString();

                msg = validations(emailStr, firstPass, secondPass);
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String validations(String email, String password, String rePassword) {
        String msg = "";
        msg += isValidEmail(email);
        msg += isValidPassword(password, rePassword);
        if(msg.equals("")) {
            msg += "Loading...";
        }
        return msg;
    }

    private String isValidEmail(CharSequence target) {
        if(!TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            return "";
        }
        else {
            email.setText("");
            firstPassword.setText("");
            secondPassword.setText("");
            return "Email not Valid ";
        }
    }

    private String isValidPassword(String password, String rePassword) {
        if(!password.equals(rePassword) || password.equals("")){
            firstPassword.setText("");
            secondPassword.setText("");
            return "The Passwords Don't Match ";
        }
        else if(password.length() < MIN_LEN) {
            firstPassword.setText("");
            secondPassword.setText("");
            return "Password must be at least six characters";
        }
        else if(!password.matches(".*\\d.*") || !password.matches(".*[a-z]")) {
            firstPassword.setText("");
            secondPassword.setText("");
            return "Password must contain characters and numbers";
        }
        else
            return "";
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
