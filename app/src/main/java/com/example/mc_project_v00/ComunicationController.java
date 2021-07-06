package com.example.mc_project_v00;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ComunicationController {
    private static final String TAG = "Comunication";
    private static final String BASE_URL = "https://ewserver.di.unimi.it/mobicomp/accordo/";

    private RequestQueue requestQueue = null;
    private Context contextContainer;





    public ComunicationController(Context c){
        Log.d(TAG, "Creating the comunication controller");
        contextContainer = c;
        requestQueue = Volley.newRequestQueue(contextContainer);
    }

    public void getWall2(String sid, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "getWall2.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void prefered (String sid, Boolean preferred, String ctitle, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "setPreferred.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);
        jsonObject.put("ctitle",ctitle);
        jsonObject.put("preferred", preferred);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void register (Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        final String service_url = "register.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void getProfile(String sid, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) throws JSONException {
        final String service_url = "getProfile.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void getWall(String sid, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "getWall.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void addChannel(String sid, String cTitle, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "addChannel.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);
        jsonObject.put("ctitle",cTitle);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void getChannel(String sid, String cTitle, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "getChannel.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid",sid);
        jsonObject.put("ctitle",cTitle);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void setProfileUsername (String sid, String name, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "setProfile.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("name", name);


        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void setProfilePicture (String sid, String picture, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "setProfile.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("picture", picture);

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void getPostImage(String sid, String pid, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "getPostImage.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("pid", pid);


        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);

    }

    public void getUserPicture (String sid, String uid, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        final String service_url = "getUserPicture.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("uid", uid);


        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void addPostText(String sid, String cTitle, String content, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) throws JSONException {
        final String service_url = "addPost.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("ctitle", cTitle);
        jsonObject.put("type", "t");
        jsonObject.put("content", content);
        //jsonObject.put("lat", null);
        //jsonObject.put("lon", null);



        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void addPostImage(String sid, String cTitle, String content, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) throws JSONException {
        final String service_url = "addPost.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("ctitle", cTitle);
        jsonObject.put("type", "i");
        jsonObject.put("content", content);
        //jsonObject.put("lat", null);
        //jsonObject.put("lon", null);



        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }

    public void addPostPosition(String sid, String cTitle, String lat, String lon, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) throws JSONException {
        final String service_url = "addPost.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", sid);
        jsonObject.put("ctitle", cTitle);
        jsonObject.put("type", "l");
        jsonObject.put("content", null);
        jsonObject.put("lat", lat);
        jsonObject.put("lon", lon);



        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, responseListener, errorListener);
        Log.d(TAG, "Sendig request" + service_url);
        requestQueue.add(request);
    }
}
