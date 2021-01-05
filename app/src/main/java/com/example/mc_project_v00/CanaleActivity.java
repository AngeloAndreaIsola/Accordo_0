package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class CanaleActivity extends AppCompatActivity {
    private static final String TAG = "BachecaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canale);

        ComunicationController ccCanale = new ComunicationController(this);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        String sidString = preferences.getString("sid", null);
        String nomeCanale = null;
        try {
            ccCanale.getChannel(sidString, nomeCanale, response -> logPostCanle(response), error -> reportErrorToUsers(error));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void logPostCanle(JSONObject response) {
        Log.d(TAG, "elenco post canale: " + response.toString());
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        //TODO  FRONT-END Mettere un TOAST per l'utente
    }
}