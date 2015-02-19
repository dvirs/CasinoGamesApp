package com.example.admin.casinogames;

import android.app.ActionBar;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.admin.casinogames.UtilClass.MyAdapter;
import com.example.admin.casinogames.UtilClass.User;
import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import com.example.admin.casinogames.com.example.admin.tasks.TopUsersTask;
import com.example.admin.casinogames.com.example.admin.tasks.getUserTask;
import com.example.admin.casinogames.com.example.admin.tasks.logoutTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class CasinoLobbyActivity extends Activity {

    private Bundle bund;
    private TextView userNameTxt, totalMoneyTxt;
    private ImageButton cubes_btn, poker_btn;
    private Button settingsBtn, highScoreBtn, logoutBtn, signInBtn, signUpBtn;
    private ArrayList userInfo;
    private ListView usersList;
    private CasinoLobbyActivity activity;
    private Bundle saveState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino_lobby);
        saveState = savedInstanceState;

        Intent intent = getIntent();
        bund = intent.getExtras();


        setViewOfFields();
        setComponentVisability(bund);
        setButtonClickable();

    }

    private void setViewOfFields() {
        activity = this;
        userNameTxt = (TextView) findViewById(R.id.hi_txt);
        totalMoneyTxt = (TextView) findViewById(R.id.total_money_txt);
        cubes_btn = (ImageButton) findViewById(R.id.cubes_btn);
        poker_btn = (ImageButton) findViewById(R.id.poker_btn);
        settingsBtn = (Button) findViewById(R.id.settings_btn);
        highScoreBtn = (Button) findViewById(R.id.high_score_btn);
        logoutBtn = (Button) findViewById(R.id.logout_btn);
        signInBtn = (Button) findViewById(R.id.sign_in_btn_lob);
        signUpBtn = (Button) findViewById(R.id.sign_up_btn_lob);
        usersList = (ListView) findViewById(R.id.user_list);


    }

    private void setComponentVisability(Bundle bund) {
        if(bund == null) {
            userNameTxt.setText(R.string.welcom_user);
            totalMoneyTxt.setText("");
            cubes_btn.setVisibility(View.INVISIBLE);
            poker_btn.setVisibility(View.INVISIBLE);
            settingsBtn.setVisibility(View.INVISIBLE);
            highScoreBtn.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            signInBtn.setVisibility(View.VISIBLE);
            signUpBtn.setVisibility(View.VISIBLE);
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
    }

    private void setButtonClickable() {
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CasinoLobbyActivity.this, LoginActivity.class);
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
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setComponentVisability(null);
                ArrayList<NameValuePair> user = new ArrayList<NameValuePair>();
                user.add(new BasicNameValuePair("id",""+userInfo.get(0)));
                user.add(new BasicNameValuePair("state",""+null));
                new logoutTask(user).execute(new apiConnectorDB());
            }
        });
        highScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService((LAYOUT_INFLATER_SERVICE));
                View popup = inflater.inflate(R.layout.popup_high_score, null);
                final PopupWindow popupWindow = new PopupWindow(popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                usersList = (ListView) popup.findViewById(R.id.user_list);


                Button close = (Button) popup.findViewById(R.id.close_btn);
                close.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(userNameTxt, 500, -100);
                new TopUsersTask(activity).execute(new apiConnectorDB());

            }
        });
        cubes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CasinoLobbyActivity.this,DiceGameActivity.class);
                i.putExtra("userinfo",userInfo);
                CasinoLobbyActivity.this.startActivity(i);
            }
        });

        poker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CasinoLobbyActivity.this, PokerActivity.class);
                i.putExtra("userinfo", userInfo);
                CasinoLobbyActivity.this.startActivity(i);
            }
        });

    }

    public void setUsersArray(ArrayList<User> topUsers){
        MyAdapter adapter = new MyAdapter(this,topUsers);
        usersList.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        //

        if(bund == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CasinoLobbyActivity.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CasinoLobbyActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(CasinoLobbyActivity.this);
            builder.setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setComponentVisability(null);
                            logout();
                            bund=null;

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            builder.create();
            builder.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUser();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(userInfo != null) {
            new getUserTask((int) userInfo.get(0), this).execute(new apiConnectorDB());
        }
    }

    private void updateUser() {
        if(userInfo != null) {
            new getUserTask((int) userInfo.get(0), this).execute(new apiConnectorDB());
        }
    }

    public void updateUserComponent(ArrayList userInfo){
        this.userInfo = userInfo;
        Log.e("de", "Found User!!! But didnt write");
        totalMoneyTxt.setText(userInfo.get(4).toString() + "$");

    }

    private void logout() {
        ArrayList<NameValuePair> user = new ArrayList<NameValuePair>();
        user.add(new BasicNameValuePair("id", "" + userInfo.get(0)));
        user.add(new BasicNameValuePair("state", "" + null));
        new logoutTask(user).execute(new apiConnectorDB());
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
