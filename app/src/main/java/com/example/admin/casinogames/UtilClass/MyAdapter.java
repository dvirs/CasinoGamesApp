package com.example.admin.casinogames.UtilClass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.casinogames.R;

import java.util.ArrayList;

/**
 * Created by omri on 22/01/2015.
 */

public class MyAdapter extends ArrayAdapter<User> {
    private Activity activity;
    private ArrayList<User> allUsers;

    public MyAdapter(Activity activity ,ArrayList<User> allUsers) {
        super(activity, R.layout.item_layout,allUsers);
        this.activity = activity;
        this.allUsers = allUsers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if(itemView == null){
            itemView = activity.getLayoutInflater().inflate(R.layout.item_layout,parent,false);
        }

        User currentUser = allUsers.get(position);
        Log.e("Debug",currentUser.toString());
            try {
                roundedImageView imageView = (roundedImageView) itemView.findViewById(R.id.imageView);
                Log.e("Debug", currentUser.getUserImage().toString());
                Bitmap imageBM = currentUser.getUserImage();
                if (imageBM != null) imageView.setImageBitmap(imageBM);
            }catch (Exception e){}




        TextView userName = (TextView) itemView.findViewById(R.id.user_name);
        userName.setText(currentUser.getName());

        TextView email = (TextView) itemView.findViewById(R.id.user_email);
        email.setText(currentUser.getEmail());

        TextView total = (TextView) itemView.findViewById(R.id.user_total);
        total.setText(""+currentUser.getTotalMoney() + "$");

        return itemView;

    }
}
