package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SettingsActivity extends AppCompatActivity{
    private static final String TAG = "SettingsActivity";
    private static int PICK_PHOTO_FOR_AVATAR = 0;

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

        loadLastProfilePicture();

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

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public void loadLastUsername(){
        SharedPreferences preferences = getSharedPreferences("User preference",MODE_PRIVATE);
        TextView userName = findViewById(R.id.usernameDisplay);
        userName.setText("");
        userName.setText(preferences.getString("username","null"));
        Log.d(TAG, "username settato a: " + preferences.getString("username","null"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            InputStream imageStream = null;
            try {
                imageStream = this.getContentResolver().openInputStream(data.getData());

                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                if (imageIsValid(yourSelectedImage)) {
                    saveImage(encodeTobase64(yourSelectedImage));

                    /*
                    ComunicationController ccSettings = new ComunicationController(this);
                    SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
                    String sid = preferences.getString("sid", null);
                    String image = preferences.getString("profileImage", null);
                    String username = preferences.getString("username", null);
                    ccSettings.setProfile(sid, username, image, response -> ProfileIsSet(), error -> reportErrorToUsers());

                     */
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

    private void saveImage(String encodeTobase64) {
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("profileImage", "");

        editor.putString("profileImage", encodeTobase64);
        editor.apply();

        Log.d(TAG, "Profile image saved:  " + preferences.getString("profileImage", null));

        loadLastProfilePicture();
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.d("LOOK", imageEncoded);
        return imageEncoded;
    }

    private void saveUsername() {
        //TODO filtrare i numi con invio e spazio
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", "");
        editor.putString("username", textView.getText().toString());
        editor.apply();

        loadLastUsername();

        Log.d(TAG, "SaveUsername username: " + preferences.getString("username", null));
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

    public void onSaveUsernameButtonClick(){
        Button settingsButton = (Button) findViewById(R.id.saveUsername);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsername();
            }
        });
    }

    public void onButtonLoadPictureClick(){
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pickImage();
            }
        });
    }

    public boolean imageIsValid (Bitmap image){
        if (image.getHeight() != image.getWidth()){
            Toast.makeText(this,"The image is NOT square", Toast.LENGTH_LONG).show();
            Log.d(TAG, "string lenght: " + encodeTobase64(image).length());
            return false;
        } else if (encodeTobase64(image).length() >= 137000) {  //500000  //TODO: TROVARE LA LUNGHEZZA DI CARATTERI GISUTA
            Log.d(TAG, "string lenght: " + encodeTobase64(image).length());
            Toast.makeText(this,"The image is bigger than 100kb", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}
