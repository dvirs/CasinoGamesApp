package com.example.admin.casinogames;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import com.example.admin.casinogames.UtilClass.utils;
import com.example.admin.casinogames.com.example.admin.tasks.InsertUserTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.logging.StreamHandler;

public class RegisterActivity extends Activity {
    private static final String REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
    private final int MIN_LEN = 6;
    private final int CAMERA_CAPTURE = 1;

    private EditText password,email,rePassword,userName;
    private TextView userNameTxt, passwordTxt, rePasswordTxt, wrongInputTxt, emailTxt;
    private Button signUp,makePhoto ;
    private ImageView picView;
    private String user,firstPass,secondPass,emailStr;
    private RegisterActivity activity;
    private String imageData;
    private Bitmap thePic;


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

              //  if(validations(emailStr, firstPass, secondPass)){
                    ArrayList<NameValuePair> userInfoArray = new ArrayList<NameValuePair>();
                    userInfoArray.add(new BasicNameValuePair("username",user));
                    userInfoArray.add(new BasicNameValuePair("email",emailStr));
                    userInfoArray.add(new BasicNameValuePair("password",firstPass));
                    userInfoArray.add(new BasicNameValuePair("image", imageData));
                    //Create a new Insert User Task
                    new InsertUserTask(userInfoArray,activity).execute(new apiConnectorDB());

             //   } else {
             //       wrongInputTxt.setText(R.string.wrong_reg);
             //   }
            }
        });

        makePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_CAPTURE);
                } catch (ActivityNotFoundException e) {
                }
            }


        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //get the returned data
        Bundle extras = data.getExtras();
        //get the cropped bitmap
        thePic = extras.getParcelable("data");
        imageData = utils.encodeTobase64(thePic);
        picView.setImageBitmap(thePic);
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
        makePhoto = (Button) findViewById(R.id.photo_btn);
        email = (EditText) findViewById(R.id.email_et);
        password = (EditText) findViewById(R.id.password_et_reg);
        rePassword = (EditText) findViewById(R.id.rePassword_et_reg);
        userName = (EditText) findViewById(R.id.userName_reg);
        emailTxt = (TextView) findViewById(R.id.email_reg);
        userNameTxt = (TextView) findViewById(R.id.userName_txt_reg);
        passwordTxt = (TextView) findViewById(R.id.password_reg);
        rePasswordTxt = (TextView) findViewById(R.id.rePassword_reg);
        wrongInputTxt = (TextView) findViewById(R.id.wrong_reg);
        picView = (ImageView)findViewById(R.id.profile_pic);
        activity = this;
    }

    private boolean validations(String email, String password, String rePassword) {
        boolean flag ;
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
