package com.example.mc_project_v00;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SponsorActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Sponsor Activity";
    String sidString;

    public Context getContext() {
        return context;
    }

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sposnsor);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);





        sidString = preferences.getString("sid", null);

        //TODO: LOG RESPONSE.SIZE
        ComunicationController cc = new ComunicationController(this);
        try {
            cc.getSponsorList(sidString, response -> {
                try {
                    saveSponsorInModel(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> reportErrorToUsers(error));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveSponsorInModel(JSONObject response) throws JSONException {
        Log.d(TAG, "Lista degli sponsor: "+ response.toString());
        JSONArray sponsorArray = response.getJSONArray("sponsors");

        Log.d(TAG,"sponsors number: " + sponsorArray.length());


        refreshSponsor();
        //SAlVA DATI NEL SPONSOR MODEL
        //SponsorModel.getInstance().addSponsor(response);

    }

    private void showSponsor(JSONObject response) throws JSONException {
        //colleghiamo model e dapter
        RecyclerView rvPost = findViewById(R.id.sponsorRecyclerView);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setHasFixedSize(true);
        SponsorAdapter sponsorAdapter = new SponsorAdapter( this, this);
        rvPost.setAdapter(sponsorAdapter);
        SponsorModel.getInstance().addSponsor(response);
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "Errore richiesta: " + error.toString());
        Toast.makeText(this,"Errore richiesta: " + error.toString(), Toast.LENGTH_LONG).show();
    }





    @Override
    public void onClick(View v) {
        Log.d("RecycleViewPost", "From Sponsor Activity: ");
    }

    private void refreshSponsor(){
        ComunicationController ccBacheca = new ComunicationController(this);
        try {
            ccBacheca.getSponsorList(sidString, response -> {
                try {
                    showSponsor(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                if (error instanceof NoConnectionError){
                    AlertDialog.Builder noConnectionDialog = new AlertDialog.Builder(this);
                    noConnectionDialog.setTitle("Nessuna connessione");
                    noConnectionDialog.setPositiveButton("Riprova", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshSponsor();
                        }
                    });
                    noConnectionDialog.setNegativeButton("Cancella",null);
                    noConnectionDialog.show();
                }else {
                    Toast.makeText(this,"Errore richiesta: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
}