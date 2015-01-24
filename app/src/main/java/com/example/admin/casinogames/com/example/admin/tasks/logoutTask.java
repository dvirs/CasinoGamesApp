package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.casinogames.UtilClass.apiConnectorDB;

import org.apache.http.NameValuePair;

import java.util.ArrayList;


/**
 * Created by omriGlam on 1/24/2015.
 */
public class logoutTask extends AsyncTask<apiConnectorDB,Long,Boolean>{

    private ArrayList<NameValuePair> user;

    public logoutTask(ArrayList<NameValuePair> user) {
        this.user = user;
    }

    @Override
    protected Boolean doInBackground(apiConnectorDB... params) {
                try{
                    params[0].setUserLoggedIn(user);
                }catch (Exception e){
                    Log.e("OMI=================================","SET USER PROBLOM");
                    return false;
                }
        return true;
    }
}
