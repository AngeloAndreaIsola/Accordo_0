package com.example.mc_project_v00;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageController extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 9;

    String uriToBase64(Uri uri) throws IOException {
        InputStream inStream = getContentResolver().openInputStream(uri);
        byte[] imageBytes = getBytes(inStream);
        String imageBase64 = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return imageBase64;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException { //URI to Bytes array
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    void getImageFromGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
}
