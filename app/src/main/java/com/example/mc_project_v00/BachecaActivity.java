package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BachecaActivity extends AppCompatActivity implements OnListClickListener {
    private static final String TAG = "BachecaActivity";
    private String sidString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Log.d(TAG, "On Create");

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
            try {
                ccBacheca.getWall(sidString, response -> TeastaRispostaPagine(response), error -> reportErrorToUsers(error));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        Model.getInstance().addFakeData();
        Log.d(TAG, "Num of contacts: " + Model.getInstance().getContactSize());

        //prova a mettere addDAta qui

        //colleghiamo model e dapter
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, this);
        rv.setAdapter(adapter);


    }



    private void TeastaRispostaPagine(JSONObject response) {
        Log.d(TAG, "request correct: "+ response.toString());
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
    public void onListClick(int position) {
        Log.d("RecycleViewExample", "From Main Activity: " + position);
    }
}












