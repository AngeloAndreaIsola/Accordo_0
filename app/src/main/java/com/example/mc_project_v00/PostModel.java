package com.example.mc_project_v00;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostModel {
    private List<JSONObject> postList;
    private static PostModel theInstance = null;
    private static final String TAG = "POST Model";

    private PostModel(){
        postList = new ArrayList<JSONObject>();
    }

    public static synchronized PostModel getInstance(){
        if(theInstance==null){
            theInstance= new PostModel();
        }
        return theInstance;
    }

    public int getPostListSize(){
        return postList.size();
    }

    public JSONObject getPostFromList(int i) throws JSONException {
        JSONObject o = postList.get(i);
        return o;
    }

    public void addPosts(JSONObject response) throws JSONException {
        postList.clear();
        JSONArray jsonArray = response.getJSONArray("posts");
        for (int i = 0; i<jsonArray.length(); i++) {
            postList.add( jsonArray.getJSONObject(i));
        }
        Log.d(TAG, "Post salvati nel postModel: " + postList.toString());
    }


}
