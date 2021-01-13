package com.example.mc_project_v00;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CanaleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChannelActivity";
    private static int PICK_PHOTO_FOR_POST = 0;
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



        ActionBar actionbar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            channelName = extras.getString("nomeCanale");
            position = extras.getInt("position");
            actionbar.setTitle(channelName);
        }

        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);


        try {
            channelName = Model.getInstance().getChannelFromList(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        refreshChat();

/*
        ComunicationController ccCanale = new ComunicationController(this);

        try {
            channelName = Model.getInstance().getChannelFromList(position);

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

 */

        //TODO: AGGIUNGERE PULSANTE REFRESH AL CANALE

        Button buttonSendPost = findViewById(R.id.buttonSendPost);
        Button buttonSendImage = findViewById(R.id.buttonSendImage);
        Button buttonSendPosition = findViewById(R.id.buttonSendPosition);
        EditText editTextMessage = findViewById(R.id.messageInputView);
        buttonSendImage.setVisibility(View.VISIBLE);
        buttonSendPosition.setVisibility(View.VISIBLE);
        buttonSendPost.setVisibility(View.INVISIBLE);

        editTextMessage.addTextChangedListener(textWatcher); //guarda se il testo cambia e nasconde le icone

        buttonSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendMessage();
                    refreshChat();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openGallery();
            }
        });
    }

    private void refreshChat() {
        try {
            ComunicationController ccCanale = new ComunicationController(context);
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

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String message = s.toString();
            if (message.length() != 0) {
                findViewById(R.id.buttonSendImage).setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonSendPosition).setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonSendPost).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.buttonSendImage).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonSendPosition).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonSendPost).setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void sendMessage() throws JSONException {
        EditText message = findViewById(R.id.messageInputView);
        String content = String.valueOf(message.getText());
        String type = "t";
        Log.d(TAG,"addPost request");
        ComunicationController cc = new ComunicationController(this);
        if(content.length() < 100) { //controlla che il testo sia piú breve di 100 caratteri
            cc.addPostText(sidString, channelName, content, response -> Log.d(TAG, "messaggio spedito"), error -> reportErrorToUsers(error) );
            //TODO: PULIRE INPUT
            EditText editTextMessage = findViewById(R.id.messageInputView);
            editTextMessage.setText("");
        } else {
            Toast.makeText(this,"The post can't be longer than 100 characters!",Toast.LENGTH_LONG).show();
        }
        //notifica che il dataset è cambiato

    }

    /*
            try {
            ccBacheca.addChannel(sidString, cTitle, response -> Log.d(TAG, "Canale " + cTitle +  " aggiunto") , error -> reportErrorToUsers(error));
        }catch (JSONException e) {
            e.printStackTrace();
        }
     */

    private void showPost(JSONObject response) throws JSONException {
        Log.d(TAG, "request correct: "+ response.toString());

        //colleghiamo model e dapter
        RecyclerView rvPost = findViewById(R.id.postRecyclerView);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setHasFixedSize(true);
        PostAdapter postAdapter = new PostAdapter(response, this, sidString, this);
        postAdapter = new PostAdapter(response, this, sidString, this);
        rvPost.setAdapter(postAdapter);

    }


    private void logAndShowChannel(String channelName, JSONObject response) {
        Log.d(TAG, "channel post list: " + channelName + ": " + response.toString());
        //TextView mTextView = findViewById(R.id.logCanale);
        //mTextView.setText(response.toString());
    }

    private void reportErrorToUsers(VolleyError error){
        Log.d(TAG, "request error: " + error.toString());
        Toast.makeText(this,"request error: " + error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        Log.d("RecycleViewPost", "From Channel Activity: " + position);
    }

    public static Context getPostActivityContext(){
        return context;
    }

    /*
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_POST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_POST && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            InputStream imageStream = null;
            try {
                imageStream = this.getContentResolver().openInputStream(data.getData());

                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                if (imageIsValid(yourSelectedImage)) {
                    sendImage(encodeTobase64(yourSelectedImage));

                    /*
                    ComunicationController ccSettings = new ComunicationController(this);
                    SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
                    String sid = preferences.getString("sid", null);
                    String image = preferences.getString("profileImage", null);
                    String username = preferences.getString("username", null);
                    ccSettings.setProfile(sid, username, image, response -> ProfileIsSet(), error -> reportErrorToUsers());


                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                //close input
                if (imageStream != null) {
                    try {
                        imageStream.close();
                    } catch (IOException ioex) {
                        //Very bad things just happened... handle it
                    }
                }
            }


        }
    }
    */
}

//TODO: PROVA CHIAMTE A CASO
//TODO: SALVARE IMMAGINI PROFILO IN CHACHE