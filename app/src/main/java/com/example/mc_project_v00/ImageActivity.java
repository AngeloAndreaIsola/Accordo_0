package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImmageActivity";
    int pid;
    String sid;
    String encodedImage=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pid = extras.getInt("pid");

        } else{
            Log.d(TAG, "Extras erano vuoti");
        }


        ComunicationController cc = new ComunicationController(this);
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        sid = preferences.getString("sid",null);

        Log.d(TAG, Integer.toString(pid));
        try {
            cc.getPostImage(sid, Integer.toString(pid), response -> {
                try {
                    hadleGetImageResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> reportErrorToUser(error));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void reportErrorToUser(VolleyError error) {
    }

    private void hadleGetImageResponse(JSONObject response) throws JSONException {
        Log.d(TAG, "request post image correct: "+ response.toString());

        //decodifica da stringa a bitmap
        String encodedImage = response.getString("content");
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView content = findViewById(R.id.imageView2);
        content.setImageBitmap(decodedByte);
    }
}