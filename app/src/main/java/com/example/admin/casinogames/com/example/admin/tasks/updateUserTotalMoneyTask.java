package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.casinogames.RegisterActivity;
import com.example.admin.casinogames.UtilClass.apiConnectorDB;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by omri on 22/01/2015.
 */
public class updateUserTotalMoneyTask extends AsyncTask<apiConnectorDB,Long,Boolean> {
    private ArrayList<NameValuePair> userInfoArray;
    private RegisterActivity activity;

    public updateUserTotalMoneyTask(ArrayList<NameValuePair> userInfoArray) {
        this.userInfoArray = userInfoArray;
        this.activity = activity;
    }

    @Override
    public Boolean doInBackground(apiConnectorDB... params) {

        return params[0].UpdateUserMoney(userInfoArray);
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {

        if(aBoolean){
            Log.e("Debug", "Update Successful");
        }else{
            Log.e("Debug","Update Not Successful");
        }
    }
}