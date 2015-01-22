package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.casinogames.RegisterActivity;
import com.example.admin.casinogames.apiConnectorDB;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by omri on 22/01/2015.
 */
public class InsertUserTask extends AsyncTask<apiConnectorDB,Long,Boolean> {
    private ArrayList<NameValuePair> userInfoArray;
    private RegisterActivity activity;

    public InsertUserTask(ArrayList<NameValuePair> userInfoArray, RegisterActivity activity) {
        this.userInfoArray = userInfoArray;
        this.activity = activity;
    }

    @Override
    public Boolean doInBackground(apiConnectorDB... params) {
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
    public void onPostExecute(Boolean aBoolean) {

        if(aBoolean){
            activity.signInSuccessful();
        }else{
            activity.signInNotSuccessful();
        }
    }
}