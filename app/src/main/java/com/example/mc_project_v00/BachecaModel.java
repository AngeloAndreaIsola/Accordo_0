package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BachecaModel {

    private List<JSONObject> channelList = null;
    private List<JSONObject> tempChannelList = null;
    private List<JSONObject> filteredList = null;
    private static BachecaModel theInstance = null;
    private static final String TAG = "Model";

    private BachecaModel(){
        channelList = new ArrayList<JSONObject>();
    }

    public static synchronized BachecaModel getInstance(){
        if(theInstance==null){
            theInstance= new BachecaModel();
        }
        return theInstance;
    }

    public int getChannelListSize(){
        return channelList.size();
    }

    public String getChannelFromList(int i) throws JSONException {
        JSONObject o = channelList.get(i);
        return o.getString("ctitle");
    }

    public boolean getChannelPreferedState(int i) throws  JSONException{
        JSONObject o = channelList.get(i);
        Log.d(TAG, "getChannelPreferedState: " + o.toString());
        if (o.getString("preferred")=="t"){
            return true;
        }else{
            return false;
        }
    }

    public  List<JSONObject> getChannelList(){
        return channelList;
    }

    public void addAndSortData(JSONObject response) throws JSONException {
        channelList.clear();
        JSONArray jsonArray = response.getJSONArray("channels");
        for (int i = 0; i<jsonArray.length(); i++) {
            channelList.add( jsonArray.getJSONObject(i));
        }

        /*
        //USA QUESTA LINEA PER TESTARE LA sortData()
        listaCanali.get(21).put("mine","t");
        listaCanali.get(22).put("mine", "t");
        listaCanali.get(19).put("mine", "t");

         */

        //channelList.sort(channelListComparator);

        Log.d(TAG, "Channel list save in model: " + channelList.toString());
    }

    public void addAndSortFilteredData (List<JSONObject> canali) throws JSONException {
        //channelList.clear();
        filteredList.clear();

        for (int i = 0; i<canali.size(); i++) {
            if (canali.get(i).getString("preferred").contains("t")){
                filteredList.add(canali.get(i));
            }
        }

        /*
        //USA QUESTA LINEA PER TESTARE LA sortData()
        listaCanali.get(21).put("mine","t");
        listaCanali.get(22).put("mine", "t");
        listaCanali.get(19).put("mine", "t");

         */

        //channelList.sort(channelListComparator);

        Log.d(TAG, "Channel list save in model: " + channelList.toString());
    }


    public static Comparator<JSONObject> channelListComparator = new Comparator<JSONObject>() {

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
}
