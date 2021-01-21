package com.example.mc_project_v00;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mc_project_v00.database.AppExecutors;
import com.example.mc_project_v00.database.DatabaseClient;
import com.example.mc_project_v00.database.PostRoomDatabase;
import com.mapbox.mapboxsdk.Mapbox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CanaleActivity extends ImageController implements View.OnClickListener {
    private static final String TAG = "ChannelActivity";
    private static int PICK_PHOTO_FOR_POST = 0;
    private static final int GALLERY_REQUEST = 9;
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
            channelName = BachecaModel.getInstance().getChannelFromList(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        refreshChat();

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
                                  //TODO: METTI IL REFRESH CHAT NELLA RESPONSE DI SENDMESSAGE
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromGallery();
            }
        });

        buttonSendPosition.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(CanaleActivity.getPostActivityContext(), SendLocationActivity.class);
                Intent i = new Intent(CanaleActivity.getPostActivityContext(), LocationComponentActivity.class);
                i.putExtra("nomeCanale", channelName);
                i.putExtra("sid",sidString);
                v.getContext().startActivity(i);
            }
        });
    }



    private void getImageFromGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the intent was to pick image, was successful and an image was picked
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null)
        {

            //Get selected image uri here
            Uri imageUri = data.getData();
            Bitmap imageBitmap =null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //imageView.setImageURI(selectedImage);

            String endodedImage = null;
            try {
                endodedImage = uriToBase64(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageIsValid(endodedImage)) {
                try {
                    postImage(endodedImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void postImage(String endodedImage) throws JSONException {
        ComunicationController comunicationController = new ComunicationController(this);
        comunicationController.addPostImage(sidString, channelName, endodedImage, response -> refreshChat(), error -> reportErrorToUsers(error));

    }


    public boolean imageIsValid (String encodedImage){

        if (encodedImage.length() >= 137000) {  //500000
            //Log.d(TAG, "string lenght (CON METODO OBSOLETO encodeTobase64) : " + encodeTobase64(image).length());
            Toast.makeText(this,"The image is bigger than 100kb", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
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
            cc.addPostText(sidString, channelName, content, response -> refreshChat(), error -> reportErrorToUsers(error));
            //TODO: PULIRE INPUT
            EditText editTextMessage = findViewById(R.id.messageInputView);
            editTextMessage.setText("");
        } else {
            Toast.makeText(this,"The post can't be longer than 100 characters!",Toast.LENGTH_LONG).show();
        }
        //notifica che il dataset è cambiato

    }


    private void showPost(JSONObject response) throws JSONException {
        Log.d(TAG, "request correct: "+ response.toString());

        //colleghiamo model e dapter
        RecyclerView rvPost = findViewById(R.id.postRecyclerView);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setHasFixedSize(true);
        PostAdapter postAdapter = new PostAdapter(response, this, sidString, this);
        rvPost.setAdapter(postAdapter);

        PostModel.getInstance().addPosts(response);
        PostModel.getInstance().addPostForDB(response);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PostRoomDatabase postRoomDatabase = DatabaseClient.getInstance(context).getPostRoomDatabase();
                try {
                    postRoomDatabase.postDao().insertAllPosts(PostModel.getInstance().getListForDB());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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

    public void writeOnDatabse() throws JSONException {
        PostRoomDatabase postRoomDatabase = DatabaseClient.getInstance(context).getPostRoomDatabase();

        for (int i=0; i<PostModel.getInstance().getListForDBsize(); i++) {
            postRoomDatabase.postDao().addPost(PostModel.getInstance().getPostFromListForDB(i));
        }

        postRoomDatabase.close();
    }

}

//TODO: PROVA CHIAMTE A CASO