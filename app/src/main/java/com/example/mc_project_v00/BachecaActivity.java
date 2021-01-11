package com.example.mc_project_v00;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class BachecaActivity extends AppCompatActivity implements OnListClickListener {

    private static final String TAG = "BachecaActivity";
    private String sidString = null;
    private MyAdapter adapter;
    private Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);
        Log.d(TAG, "On Create");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("ACCORDO");

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        sidString = preferences.getString("sid", null);
        adapter = new MyAdapter(this, this);
        context = this;

        onButtonClickAddChannel();

        //PER TESTARE IL SALVATAGGIO DEL SID, RESET SHARED PREFERENCE
        /*
        editor.clear();
        editor.apply();
         */

        //CONTROLLA CHE SIA IL PRIMO ACCESSO DELL'UTENTE
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
                        showWall(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> reportErrorToUsers(error));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bacheca, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                Toast.makeText(context, "Refreshing wall", Toast.LENGTH_SHORT).show();
                refreshWall();
                break;

            case R.id.addChannel:

                break;

            case R.id.settings:
                startActivity(new Intent(BachecaActivity.this, SettingsActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void LogNewChannel(JSONObject response) {
        Log.d(TAG, "addChannel() response: " + response.toString());
    }

    private void showWall(JSONObject response) throws JSONException {   //TODO: PENSA A UN NOME MIGLIORE
        Log.d(TAG, "request correct: "+ response.toString());

        //colleghiamo model e dapter
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, this);
        rv.setAdapter(adapter);

        Model.getInstance().addAndSortData(response);
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        Toast.makeText(this,"request error: " + error.toString(), Toast.LENGTH_LONG).show();
    }

    public void saveSID_inSharedPreferences(JSONObject response) {
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString("sid", response.getString("sid"));
            editor.apply();
        } catch (JSONException e) {
            Log.d(TAG, "parsing failed");
        }
        Log.d(TAG, "request correct: "+ response.toString());
        Log.d(TAG, "request saved: "+ preferences.getAll().toString());
    }

    @Override
    public void onListClick(int position) throws JSONException {
        Log.d("RecycleViewExample", "From Main Activity: " + position);

        String nomeCanale = Model.getInstance().getChannelFromList(position);
        Intent i = new Intent(BachecaActivity.this, CanaleActivity.class);
        i.putExtra("nomeCanale", nomeCanale);
        i.putExtra("position", position);
        startActivity(i);

    }

    private void onButtonClickAddChannel() {

    }

    private void refreshWall(){
        ComunicationController ccBacheca = new ComunicationController(context);
        try {
            ccBacheca.getWall(sidString, response -> {
                try {
                    showWall(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> reportErrorToUsers(error));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}


//TODO: COMPORTAMENTO STRANO QUANDO CHANNELLIST VUOTA, SEGNA CHE L'ADAPTER NON SIA COLELGATO E SKIPPA IL LAYOUT









