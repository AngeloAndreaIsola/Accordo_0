package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class BachecaActivity extends AppCompatActivity implements OnListClickListener {
    private static final String TAG = "BachecaActivity";
    private String sidString = null;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Log.d(TAG, "On Create");

        adapter = new MyAdapter(this, this);

        onButtonClickSettings();
        onButtonClickRerfesh();

        //CONTROLLA CHE SIA IL PRIMO ACCESSO DELL'UTENTE
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //PER TESTARE IL SALVATAGGIO DEL SID, RESET SHARED PREFERENCE
        /*
        editor.clear();
        editor.apply();
         */

        ComunicationController ccBacheca = new ComunicationController(this);
        if (preferences.getBoolean("firstLogin", true)) {

            ccBacheca.register(response -> saveSID_inSharedPreferences(response), error -> reportErrorToUsers(error));

            editor.putBoolean("firstLogin", false);
            editor.commit();
        }


        if (preferences.getString("sid",null) != null){
            sidString = preferences.getString("sid", null);

            /*
            //TEST ADD CHANNEL
            String nomeCanale = "Apple";
            try {
                ccBacheca.addChannel(sidString, nomeCanale, response -> LogNewChannel(response), error -> reportErrorToUsers(error));
            } catch (JSONException e) {
                e.printStackTrace();
            }

             */


            try {
                ccBacheca.getWall(sidString, response -> {
                    try {
                        teastaRispostaPagine(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> reportErrorToUsers(error));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





    }


    private void LogNewChannel(JSONObject response) {
        Log.d(TAG, "Risposta addChannel(): " + response.toString());
    }
    private void teastaRispostaPagine(JSONObject response) throws JSONException {
        Log.d(TAG, "request correct: "+ response.toString());

        //colleghiamo model e dapter
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //MyAdapter adapter = new MyAdapter(this, this);
        rv.setAdapter(adapter);

        Model.getInstance().addAndSortData(response);
    }
    private void informTheUserAboutTheSID(JSONObject response) {
        Log.d(TAG, "request correct: "+ response.toString());
    }
    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        //TODO  FRONT-END Mettere un TOAST per l'utente
    }
    public void saveSID_inSharedPreferences(JSONObject response) {
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString("sid", response.getString("sid"));
            editor.apply();
        } catch (JSONException e) {
            Log.d(TAG, "parsing fallito");
        }
        Log.d(TAG, "request correct: "+ response.toString());
        Log.d(TAG, "request saved: "+ preferences.getAll().toString());
    }

    @Override
    public void onListClick(int position) throws JSONException {
        Log.d("RecycleViewExample", "From Main Activity: " + position);
        //QUI GLI DIRO DI APRIRE IL CANALE SU CUI CLICCO
        String nomeCanale = Model.getInstance().getCanaleDaLista(position);
        ComunicationController ccCanale = new ComunicationController(this);
        ccCanale.getChannel(sidString, nomeCanale, response -> Log.d(TAG, "elenco post canale: " + response.toString()), error -> reportErrorToUsers(error));
        //startActivity(new Intent(BachecaActivity.this, CanaleActivity.class));

    }
    public void onButtonClickSettings(){
        Button settingsButton = (Button) findViewById(R.id.goToSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BachecaActivity.this, SettingsActivity.class));
            }
        });
    }

    private void onButtonClickRerfesh() {
        Button refreshButton = (Button) findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Model.getInstance().testRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}












