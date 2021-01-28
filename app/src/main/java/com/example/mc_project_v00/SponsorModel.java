package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SponsorModel {
    private List<JSONObject> sponsorList = null;
    private static SponsorModel theInstance = null;
    private static final String TAG = "Sponsor Model";

    private SponsorModel(){
        sponsorList = new ArrayList<JSONObject>();
    }

    public static synchronized SponsorModel getInstance(){
        if(theInstance==null){
            theInstance= new SponsorModel();
        }
        return theInstance;
    }

    public int getChannelListSize(){
        return sponsorList.size();
    }

    public JSONObject getSponsorObjFromList(int i) throws JSONException {
        JSONObject o = sponsorList.get(i);
        return o;
    }

    public void addSponsor(JSONObject response) throws JSONException {
        sponsorList.clear();
        JSONArray jsonArray = response.getJSONArray("sponsors");
        for (int i = 0; i<jsonArray.length(); i++) {
            sponsorList.add( jsonArray.getJSONObject(i));
        }


        Log.d(TAG, "Sponsor list save in model: " + sponsorList.toString());
    }

}
