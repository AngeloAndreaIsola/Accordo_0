package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<String> contacts = null;
    private List<JSONObject> listaCanali = null;
    private static Model theInstance = null;
    private static final String TAG = "Model";

    private  Model(){
        contacts = new ArrayList();
        listaCanali = new ArrayList<>();
    }
    public static synchronized Model getInstance(){
        if(theInstance==null){
            theInstance= new Model();
        }
        return theInstance;
    }

    public int getListaCanaliSize(){
        return listaCanali.size();
    }

    public String getCanaleDaLista(int i) throws JSONException {
        JSONObject o = listaCanali.get(i);
        return o.getString("ctitle");
    }
    public void addData(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("channels");
        for (int i = 0; i<jsonArray.length(); i++) {
            listaCanali.add( jsonArray.getJSONObject(i));
        }
        Log.d(TAG, "Lista canali salvata nel model: " + listaCanali.toString());
    }
}
