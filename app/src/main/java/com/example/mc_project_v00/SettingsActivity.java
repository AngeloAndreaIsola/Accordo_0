package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SettingsActivity extends AppCompatActivity{
    private static final String TAG = "SettingsActivity";
    private static int PICK_IMAGE = 1;
    private static int PICK_PHOTO_FOR_AVATAR = 0;


    private ImageView imageView;
    private TextView textView;
    String username = null;
    private View usernametextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        onBackToBachecaButtonClick();
        onSaveUsernameButtonClick();


        textView = findViewById((R.id.username));
        textView.setHint("inserisci username");

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                 */
                pickImage();

            }
        });

    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
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
                //fai cose qui
                imageStream = this.getContentResolver().openInputStream(data.getData());

                Log.d(TAG,"SONO QUI");

                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                encodeTobase64(yourSelectedImage);


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
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", textView.getText().toString());
        //editor.putString("username", "toString");
        editor.apply();

        TextView displayUsername = findViewById(R.id.usernameDisplay);
        displayUsername.setText(preferences.getString("Username",null));

        Log.d(TAG, "username: " + preferences.getString("username", null));
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




}
