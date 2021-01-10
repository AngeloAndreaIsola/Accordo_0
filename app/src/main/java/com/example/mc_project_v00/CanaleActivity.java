package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class CanaleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChannelActivity";
    private static Context context;
    private int position;
    private String channelName = null;
    private String sidString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canale);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        sidString = preferences.getString("sid", null);
        context = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            channelName = extras.getString("nomeCanale");
            position = extras.getInt("position");
            TextView mTextView = findViewById(R.id.nomeCanale);
            mTextView.setText(channelName);
        }


        ComunicationController ccCanale = new ComunicationController(this);

        try {
            channelName = Model.getInstance().getChannelFromList(position);

            String finalChannelName = channelName;
            ccCanale.getChannel(sidString, channelName, response -> {
                try {
                    showPost(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> reportErrorToUsers(error));  //logAndShowChannel(finalChannelName, response)

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showPost(JSONObject response) throws JSONException {
        Log.d(TAG, "request correct: "+ response.toString());

        //colleghiamo model e dapter
        RecyclerView rvPost = findViewById(R.id.postRecyclerView);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setHasFixedSize(true);
        PostAdapter postAdapter = new PostAdapter(response, this, sidString, this);
        rvPost.setAdapter(postAdapter);

    }


    private void logAndShowChannel(String channelName, JSONObject response) {
        Log.d(TAG, "channel post list: " + channelName + ": " + response.toString());
        //TextView mTextView = findViewById(R.id.logCanale);
        //mTextView.setText(response.toString());
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        Toast.makeText(this,"request error: " + error.toString(), Toast.LENGTH_LONG).show();    }

    @Override
    public void onClick(View v) {
        //a seconda del tipo di view fai partire l'activy giusta
        Log.d("RecycleViewPost", "From Channel Activity: " + position);



        /*
         Log.d("RecycleViewExample", "From Main Activity: " + position);

        String nomeCanale = Model.getInstance().getChannelFromList(position);
        Intent i = new Intent(BachecaActivity.this, CanaleActivity.class);
        i.putExtra("nomeCanale", nomeCanale);
        i.putExtra("position", position);
        startActivity(i);
         */
    }

    public static Context getPostActivityContext(){
        return context;
    }
}