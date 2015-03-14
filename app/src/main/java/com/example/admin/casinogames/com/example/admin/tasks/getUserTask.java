package com.example.admin.casinogames.com.example.admin.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.admin.casinogames.CasinoLobbyActivity;
import com.example.admin.casinogames.UtilClass.apiConnectorDB;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by omriGlam on 2/19/2015.
 */
public class getUserTask extends AsyncTask<apiConnectorDB,Long,ArrayList> {
    private int userID;
    private CasinoLobbyActivity activity;

    public getUserTask(int userID,CasinoLobbyActivity activity){
        this.userID = userID;
        this.activity = activity;
    }

    @Override
    protected ArrayList doInBackground(apiConnectorDB... params) {
        JSONArray jsonArray = params[0].getAllUsers();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(i);
                    if (json.getString("id").equals(userID+"")) {

                            Log.e("de","Found User!!!");
                            ArrayList userInfo = new ArrayList();
                            try {
                                userInfo.add(json.getInt("id"));
                                userInfo.add(json.getString("username"));
                                userInfo.add(json.getString("email"));
                                userInfo.add(json.getString("password"));
                                userInfo.add(json.getInt("totalmoney"));
                                userInfo.add(json.getString("image"));

                            } catch (Exception e) {
                                Log.e("Debug", "Didnt parse the Json to ArryList");
                            }
                            return userInfo;
                        }
                } catch (Exception e) {
                    Log.e("Debug", "Faild getting the Json Object");
                }
            }
        }
        Log.e("de","Didnt Found User!!!");
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {

        if(arrayList!=null) activity.updateUserComponent(arrayList);
    }
}
