package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BachecaActivity extends AppCompatActivity {
    private static final String TAG = "BachecaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Log.d(TAG, "On Create");

        //FA E GESTISCE LE RICHIESTE
        ComunicationController ccBacheca = new ComunicationController(this);
        ccBacheca.register(response -> saveSID(response), error -> reportErrorToUsers(error));

    }

    private void informTheUserAboutTheSID(JSONObject response) {
        Log.d(TAG, "request correct: "+ response.toString());
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        //TODO  FRONT-END Mettere un TOAST per l'utente
    }
    public void saveSID(JSONObject response) {
        SharedPreferences sp = getSharedPreferences("User preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString("sid", response.getString("sid"));  //TODO: VA PARSERIZZATO
        } catch (JSONException e) {
            Log.d(TAG, "parsing fallito");
        }
        editor.commit();
        Log.d(TAG, "request correct: "+ response.toString());
        Log.d(TAG, "request saved: "+ sp.getAll().toString());
    }
}















//TODO BACK-END Controllare se è il primo accesso dell'utente
//TODO BACK-END Richiedere SID (fai un ComunicationController)
//TODO BACK-END Salva SID (come singleton)
//TODO BACK-END Scrivere test registrazione
//TODO BACK-END Implementare reclyer view

//TODO FRONT-END Gestire pulsanti
//TODO Gestire refresh pagina