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
                String user = userName.getText().toString();
                String emailStr = email.getText().toString();
                String firstPass = firstPassword.getText().toString();
                String secondPass = secondPassword.getText().toString();
                if(!isValidEmail(emailStr)) {
                    Toast.makeText(RegisterActivity.this, "Email not Valid", Toast.LENGTH_SHORT).show();
                    firstPassword.setText("");
                    secondPassword.setText("");
                }
                if(!firstPass.equals(secondPass) || firstPass.equals("")){
                    Toast.makeText(RegisterActivity.this,"The Passwords Dont Match",Toast.LENGTH_LONG).show();
                    firstPassword.setText("");
                    secondPassword.setText("");

                }else{
                    Toast.makeText(RegisterActivity.this,"Loading...",Toast.LENGTH_LONG).show();
                    signUp.setClickable(false);
                    signUp.setFocusable(false);
                }
            }
        });


    }
    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
