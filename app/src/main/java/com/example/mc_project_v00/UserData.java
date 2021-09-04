package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;

public class UserData {
    private static final String TAG = "UserData";

    private String sid = null;
    private int uid = 0;
    private String username = null;
    private String picture = null;
    private int pversion = 0;

    static public JSONArray f = new JSONArray();

    public void saveProfile(int uid, String username, String picture, int pversion){
        this.uid = uid;
        this.username = username;
        this.picture = picture;
        this.pversion = pversion;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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
