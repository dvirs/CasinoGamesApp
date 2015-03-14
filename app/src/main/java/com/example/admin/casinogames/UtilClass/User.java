package com.example.admin.casinogames.UtilClass;

import android.graphics.Bitmap;

/**
 * Created by omri on 22/01/2015.
 */
public class User {
    private String name;
    private String email;
    private int totalMoney;
    private Bitmap userImage;

    public User(String email, String name, int totalMoney, Bitmap userImage) {
        this.email = email;
        this.name = name;
        this.totalMoney = totalMoney;
        this.userImage = userImage;

    }

    public String getName() {
        return name;
    }

    public Bitmap getUserImage(){return userImage;}

    public String getEmail() {
        return email;
    }

    public int getTotalMoney() {
        return totalMoney;
    }
}