package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.admin.casinogames.CasinoLobbyActivity;
import com.example.admin.casinogames.R;
import com.example.admin.casinogames.UtilClass.User;
import com.example.admin.casinogames.apiConnectorDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by omri on 22/01/2015.
 */
public class TopUsersTask extends AsyncTask<apiConnectorDB,Long,ArrayList> {
    private CasinoLobbyActivity activity;
    private ArrayList<User> topUsers;

    public TopUsersTask(CasinoLobbyActivity activity) {
        this.activity = activity;

    }

    @Override
    protected ArrayList doInBackground(apiConnectorDB... params) {
        JSONArray jsonArray = params[0].getTopUsers();
        topUsers = new ArrayList<>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject json = jsonArray.getJSONObject(i);
                    topUsers.add(new User("",json.getString("username"),json.getInt("totalmoney")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return topUsers;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        activity.setUsersArray(arrayList);

    }
}