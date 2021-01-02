package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Model {
    private List<JSONObject> listaCanali = null;
    private static Model theInstance = null;
    private static final String TAG = "Model";

    private  Model(){
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

        sortData(listaCanali);

        Log.d(TAG, "Lista canali salvata nel model: " + listaCanali.toString());
    }

    public void sortData(List<JSONObject> lista){
        Collections.sort(lista, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String o1_string = null;
                String o2_string = null;
                boolean b1 = Boolean.valueOf(o1_string);
                boolean b2 = Boolean.valueOf(o2_string);

                try {
                    b1 = o1.getString("mine").contains("t");
                    b2 = o2.getString("mine").contains("f");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return Boolean.compare(b1 ,b2);
            }
        });
        Log.d(TAG, "Lista canali riordinata è: " + listaCanali.toString());
    }



}
