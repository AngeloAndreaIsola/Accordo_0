package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<String> contacts = null;
    private static Model theInstance = null;
    private static final String TAG = "Model";

    private  Model(){
        contacts = new ArrayList();
    }
    public static synchronized Model getInstance(){
        if(theInstance==null){
            theInstance= new Model();
        }
        return theInstance;
    }


    public void addFakeData(){
        for(int i=0; i<100; i++){
            contacts.add("contact "+ i);
        }
    }

    public int getContactSize(){
        return contacts.size();
    }

    public String getContact(int i){
        return contacts.get(i);
    }
    public void addData(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("channels");
        for (int i = 0; i<jsonArray.length(); i++) {
            contacts.add( jsonArray.getJSONObject(i).toString());
        }
        Log.d(TAG, "Lista canali salvata nel model: " + contacts.toString());
    }
}
