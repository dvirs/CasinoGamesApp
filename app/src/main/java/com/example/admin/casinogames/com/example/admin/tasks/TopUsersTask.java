package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.admin.casinogames.R;
import com.example.admin.casinogames.apiConnectorDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by omri on 22/01/2015.
 */
public class TopUsersTask extends AsyncTask<apiConnectorDB,Long,ArrayList> {

    @Override
    protected ArrayList doInBackground(apiConnectorDB... params) {
        JSONArray jsonArray = params[0].getTopUsers();
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    arrayList.add(i, jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        Log.e("debugggggggg", "array = " + arrayList.toString());
        String[] values = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            values[i] = arrayList.get(i).toString();
        }
        Log.e("debugggggggg", "values = " + values.toString());

    }
}