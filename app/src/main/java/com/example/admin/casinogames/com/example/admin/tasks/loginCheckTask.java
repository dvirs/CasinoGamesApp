package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.casinogames.MainActivity;
import com.example.admin.casinogames.apiConnectorDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by omri on 22/01/2015.
 */

public class loginCheckTask extends AsyncTask<apiConnectorDB,Long,ArrayList> {
    private String userName,password;
    private MainActivity activity;

    public loginCheckTask(String userName, String password, MainActivity mainActivity) {
        this.userName = userName;
        this.password = password;
        this.activity = mainActivity;

    }

    @Override
    public ArrayList<String> doInBackground(apiConnectorDB... params) {
        JSONArray jsonArray = params[0].getAllUsers();
        if(jsonArray != null){
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject json = null;
                try{
                    json = jsonArray.getJSONObject(i);
                    if((json.getString("username").equals(userName) || json.getString("email").equals(userName)) && json.getString("password").equals(password)){


                            //Create an Arraylist To pass The loged user info
                            ArrayList userInfo = new ArrayList();
                            try {
                                userInfo.add(json.getInt("id"));
                                userInfo.add(json.getString("username"));
                                userInfo.add(json.getString("email"));
                                userInfo.add(json.getString("password"));
                                userInfo.add(json.getInt("totalmoney"));

                            }catch (Exception e){
                                Log.e("Debug","Didnt parse the Json to ArryList");
                            }
                        return userInfo;
                    }
                }catch (Exception e){
                    Log.e("Debug", "Faild getting the Json Object");
                }
            }
        }
        return null;

    }

    @Override
    public void onPostExecute(ArrayList userInfo) {
        activity.loginResult(userInfo);
    }
}