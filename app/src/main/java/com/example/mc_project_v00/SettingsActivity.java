package com.example.mc_project_v00;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity{
    private static final String TAG = "SettingsActivity";


    TextView textView;
    String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        onBackToBachecaButtonClick();
        onSaveUsernameButtonClick();

        textView = findViewById((R.id.username));
        textView.setHint("inserisci username");


    }

    private void saveUsername() {
        SharedPreferences preferences = getSharedPreferences("User preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", textView.getText().toString());
        //editor.putString("username", "toString");
        editor.apply();

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
