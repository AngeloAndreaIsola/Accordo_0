package com.example.mc_project_v00;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.io.IOException;

public class SettingsActivity extends ImageController {
    private static final String TAG = "SettingsActivity";
    private static final int GALLERY_REQUEST = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        SharedPreferences preferences = getSharedPreferences("User preference",MODE_PRIVATE);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Impostazioni");
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);



        ImageView profileImageView = findViewById(R.id.imageView);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getImageFromGallery();
            }
        });

        Context context = this;
        TextView usernameTextView = findViewById(R.id.usernameDisplay);
        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d(TAG, "click su username");
                AlertDialog.Builder channelDialog = new AlertDialog.Builder(context);
                channelDialog.setTitle("Cambia username: ");
                final EditText username = new EditText(context);
                username.setInputType(InputType.TYPE_CLASS_TEXT);
                channelDialog.setView(username);
                channelDialog.setPositiveButton("Aggingi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String usernameString= username.getText().toString();
                        Log.d(TAG, usernameString);
                        saveUsername(usernameString);
                        try {
                            uploadUsername();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                channelDialog.setNegativeButton("Cancella",null);
                channelDialog.show();
            }
        });





        if (preferences.getString("username", null) != null){
            loadLastUsername();
        }

        if (preferences.getString("profileImage", null) != null){
            loadLastProfilePicture();
        }else{
            profileImageView.setImageResource(R.drawable.ic_baseline_account_box_24);
        }


    }


    private void saveImage(String encodeTobase64) {
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("profileImage", "");

        editor.putString("profileImage", encodeTobase64);
        editor.apply();

        Log.d(TAG, "Profile image saved:  " + preferences.getString("profileImage", null));

    }
    private void loadLastProfilePicture() {
        SharedPreferences preferences = getSharedPreferences("User preference",MODE_PRIVATE);

        //decodifica da stringa a bitmap
        String encodedImage = preferences.getString("profileImage", null);
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(decodedByte);

        Log.d(TAG, "Profile image set");
    }
    private void uploadImage() throws JSONException {
        ComunicationController cc = new ComunicationController(this);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        String sid = preferences.getString("sid", null);
        String image = preferences.getString("profileImage", null);

        cc.setProfilePicture(sid, image, response -> Log.d(TAG, "Profile image uploaded to server"), error -> reportErrorToUser(error) );
    }
    public boolean imageIsValid (String encodedImage, Bitmap imageBitmap){   //TODO: DA CAMBIARE QUI

        if (imageBitmap.getHeight() != imageBitmap.getWidth()){
            Toast.makeText(this,"L'immagine NON è qudrata", Toast.LENGTH_LONG).show();
            return false;
        } else if (encodedImage.length() >= 137000) {
            Toast.makeText(this,"Immagine piu grande di 100kb", Toast.LENGTH_LONG).show();
            return false;
        }else try {
                //decodifica da stringa a bitmap
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                return true;
            } catch (IllegalArgumentException e) {
                Toast.makeText(this,"Codifica base64 errata", Toast.LENGTH_LONG).show();
                return false;
            }
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


            String endodedImage = null;
            try {
                endodedImage = uriToBase64(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageIsValid(endodedImage, imageBitmap)) {
            saveImage(endodedImage);
            loadLastProfilePicture();
            try {
                uploadImage();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        }
    }


    private void saveUsername(String username) {
        //TODO filtrare i nomi con invio e spazio
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", "");
        editor.putString("username", username);
        editor.apply();

        loadLastUsername();

        Log.d(TAG, "SaveUsername username: " + preferences.getString("username", null));
    }

    private void uploadUsername() throws JSONException {
        ComunicationController cc = new ComunicationController(this);

        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        String sid = preferences.getString("sid", null);
        String username = preferences.getString("username", null);

        cc.setProfileUsername(sid, username, response -> Log.d(TAG, "Profile username uploaded to server"), error -> reportErrorToUser(error) );
        //TODO: GESTISCI L'ERRORE NEW CASO L'USERNAME SEISTA GIA
    }

    public void loadLastUsername(){
        SharedPreferences preferences = getSharedPreferences("User preference",MODE_PRIVATE);
        TextView userName = findViewById(R.id.usernameDisplay);
        userName.setText("");
        userName.setText(preferences.getString("username","null"));
        Log.d(TAG, "username settato a: " + preferences.getString("username","null"));
    }


    private void reportErrorToUser(VolleyError error) {
            Log.d(TAG, "request error: " + error.toString());
            Toast.makeText(this,"Errore upload: " + error.toString(), Toast.LENGTH_LONG).show();
    }


}
