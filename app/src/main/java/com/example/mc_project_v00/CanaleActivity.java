package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class CanaleActivity extends AppCompatActivity {
    private static final String TAG = "CanaleActivity";
    private int position;
    private String sidString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canale);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        sidString = preferences.getString("sid", null);
        String nomeCanale = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomeCanale = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        try {
            nomeCanale = Model.getInstance().getCanaleDaLista(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ComunicationController ccCanale = new ComunicationController(this);

        try {
            String finalNomeCanale = nomeCanale;
            ccCanale.getChannel(sidString, nomeCanale, response -> Log.d(TAG, "elenco post del canale " + finalNomeCanale + ": " + response.toString()), error -> reportErrorToUsers(error));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        //TODO  FRONT-END Mettere un TOAST per l'utente
    }
}