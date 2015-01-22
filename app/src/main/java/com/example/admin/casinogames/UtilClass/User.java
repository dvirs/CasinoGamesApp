package com.example.admin.casinogames.UtilClass;

/**
 * Created by omri on 22/01/2015.
 */
public class User {
    private String name;
    private String email;
    private int totalMoney;

    public User(String email, String name, int totalMoney) {
        this.email = email;
        this.name = name;
        this.totalMoney = totalMoney;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getTotalMoney() {
        return totalMoney;
    }
}