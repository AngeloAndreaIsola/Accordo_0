package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Model {

    private List<JSONObject> listaCanali = null;
    private static Model theInstance = null;
    private static final String TAG = "Model";

    private  Model(){
        listaCanali = new ArrayList<JSONObject>();
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

    public void addAndSortData(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("channels");
        for (int i = 0; i<jsonArray.length(); i++) {
            listaCanali.add( jsonArray.getJSONObject(i));
        }

        /*
        //USA QUESTA LINEA PER TESTARE LA sortData()
        listaCanali.get(21).put("mine","t");
        listaCanali.get(22).put("mine", "t");
        listaCanali.get(19).put("mine", "t");

         */

        listaCanali.sort(listaCanaliComparator);

        Log.d(TAG, "Lista canali salvata nel model: " + listaCanali.toString());
    }


    public static Comparator<JSONObject> listaCanaliComparator = new Comparator<JSONObject>() {

        public int compare(JSONObject o1, JSONObject o2) {
            String s1 = null;
            String s2 = null;
            try {
                s1 = o1.getString("mine");
                s2 = o2.getString("mine");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //ascending order
            //return StudentName1.compareTo(StudentName2);
            return s2.compareTo(s1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    public void testRefresh() throws JSONException {
        listaCanali.get(21).put("mine","t");
        listaCanali.get(22).put("mine", "t");
        listaCanali.get(19).put("mine", "t");
        listaCanali.sort(listaCanaliComparator);
        Log.d(TAG, "addFakeData: " + listaCanali.toString());

    }

}
