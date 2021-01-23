package com.example.mc_project_v00;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.io.IOException;

public class SettingsActivity extends ImageController {
    private static final String TAG = "SettingsActivity";
    private static final int GALLERY_REQUEST = 9;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        SharedPreferences preferences = getSharedPreferences("User preference",MODE_PRIVATE);


        onBackToBachecaButtonClick();
        onSaveUsernameButtonClick();
        onButtonLoadPictureClick();

        textView = findViewById((R.id.username));
        textView.setHint("insert username");

        if (preferences.getString("username", null) != null){
            loadLastUsername();
        }

        if (preferences.getString("profileImage", null) != null){
            loadLastProfilePicture();
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
            Toast.makeText(this,"The image is NOT square", Toast.LENGTH_LONG).show();
            //Log.d(TAG, "string lenght (CON METODO OBSOLETO encodeTobase64) : " + encodeTobase64(image).length());
            return false;
        } else if (encodedImage.length() >= 137000) {
            //Log.d(TAG, "string lenght (CON METODO OBSOLETO encodeTobase64) : " + encodeTobase64(image).length());
            Toast.makeText(this,"The image is bigger than 100kb", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    public void onButtonLoadPictureClick(){
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //pickImage();
                getImageFromGallery();
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


    private void saveUsername() {
        //TODO filtrare i nomi con invio e spazio
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", "");
        editor.putString("username", textView.getText().toString());
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
    public void onSaveUsernameButtonClick(){
        Button settingsButton = (Button) findViewById(R.id.saveUsername);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsername();
                try {
                    uploadUsername();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void reportErrorToUser(VolleyError error) {
            Log.d(TAG, "request error: " + error.toString());
            Toast.makeText(this,"upload error: " + error.toString(), Toast.LENGTH_LONG).show();
    }



    public void onBackToBachecaButtonClick(){
        Button settingsButton = (Button) findViewById(R.id.backToBacheca);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
