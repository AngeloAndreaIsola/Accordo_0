package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SponsorDetailActivity extends AppCompatActivity {

    private static final String TAG = "SPONSOR DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_detail);

        String text= null, image = null;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String  url  =extras.getString("url");
            final String url_web = "https://"+url;
            text =  extras.getString("text");
            image =  extras.getString("image");

            ImageView imageView = findViewById(R.id.sponsor_details_profileImage);
            TextView urlTextView = findViewById(R.id.sponsor_details_username);
            TextView textTextView = findViewById(R.id.sponsor_details_text);

            try {
                //decodifica da stringa a bitmap
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, "BASE 64 SBAGLIATO");
                imageView.setImageResource(R.drawable.ic_baseline_broken_image_24);
            }

            urlTextView.setText(url);
            textTextView.setText(text);

            urlTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW", Uri.parse(url_web));
                    startActivity(viewIntent);

                }
            });

        } else{
            finish();
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}