package com.example.admin.casinogames;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by omriGlam on 1/13/2015.
 */
public class apiConnectorDB {

    public Boolean InsertUser(ArrayList<NameValuePair> list){
        String url = "http://omriglam.netau.net/insert_user.php";
        HttpEntity httpEntity= null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            Log.e("Debug", "connected insert User!");
        } catch (Exception e) {
            Log.e("Debug","Didnt connected! " + e.toString());
            return false;
        }
        return true;
    }

    public JSONArray getAllUsers() {

        String url = "http://omriglam.netau.net/get_users.php"; //External Database
        // String url = "http://192.168.1.11:81/test.php";  //Localhost Database
        HttpEntity httpEntity= null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            Log.e("Debug", "connected!");
        } catch (Exception e) {

            Log.e("Debug","Didnt connected! " + e.toString());

        }

        JSONArray jsonArray = null;

        if(httpEntity != null){
            try{

                String entityResponse = EntityUtils.toString(httpEntity);
                Log.e("Debug","httpEntity is not empty: \n"+entityResponse);
                jsonArray = new JSONArray(entityResponse);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Log.e("Debug","httpEntity is EMPTY");
        }
        return jsonArray;

    }
}
