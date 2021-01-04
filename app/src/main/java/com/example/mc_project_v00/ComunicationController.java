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

    public void register (Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
        final String service_url = "register.php";
        final String url = BASE_URL + service_url;
        final JSONObject jsonObject = new JSONObject();

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

}
