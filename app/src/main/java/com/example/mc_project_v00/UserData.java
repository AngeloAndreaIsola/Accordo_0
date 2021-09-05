package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;

public class UserData {
    private static final String TAG = "UserData";

    static public String sid = null;
    static public String uid = null;
    static public String username = null;
    static public String picture = null;
    static public int pversion = 0;

    public void saveProfile(String uid, String username, String picture, int pversion){
        this.uid = uid;
        this.username = username;
        this.picture = picture;
        this.pversion = pversion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPversion() {
        return pversion;
    }

    public void setPversion(int pversion) {
        this.pversion = pversion;
    }

    public void saveSid(String sid){
        this.sid = sid;
        Log.d(TAG, "Sid save in userdata");
    }

}
