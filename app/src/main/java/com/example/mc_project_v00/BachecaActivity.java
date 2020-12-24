package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class BachecaActivity extends AppCompatActivity {
    private static final String TAG = "BachecaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Log.d(TAG, "On Create");

        ComunicationController cc = new ComunicationController(this);
        cc.register(response -> informTheUserAboutTheSID(response), error -> reportErrorToUsers(error));


    }

    private void informTheUserAboutTheSID(JSONObject response) {
        Log.d(TAG, "request correct: "+ response.toString());
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        //TODO  FRONT-END Mettere un TOAST per l'utente
    }
}

//TODO BACK-END Controllare se è il primo accesso dell'utente
//TODO BACK-END Richiedere SID (fai un ComunicationController)
//TODO BACK-END Salva SID (come singleton)
//TODO BACK-END Scrivere test registrazione

//TODO FRONT-END Gestire pulsanti
//TODO Gestire refresh pagina