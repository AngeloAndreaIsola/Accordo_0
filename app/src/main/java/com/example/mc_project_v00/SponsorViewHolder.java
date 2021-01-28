package com.example.mc_project_v00;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class SponsorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final String TAG = "SponsorViewHolder";
    TextView textViewUsername, textViewContent;
    ImageView imageViewProfileImage;
    View.OnClickListener mClickListener = null;
    int p;


    public SponsorViewHolder(@NonNull View itemView, View.OnClickListener clicklistener) {
        super(itemView);
        textViewUsername = itemView.findViewById(R.id.sponsor_Username);
        textViewContent = itemView.findViewById(R.id.sponsor_text);
        imageViewProfileImage = itemView.findViewById((R.id.sponsor_ProfileImage));


        itemView.setOnClickListener(this);
        mClickListener = clicklistener;


    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Hai cliccato sul link dello sponsor");

        Intent i = new Intent(v.getContext(), SponsorDetailActivity.class);

        String url = null;
        String text= null;
        String image= null;

        try {
            JSONObject o = SponsorModel.getInstance().getSponsorObjFromList(getAdapterPosition());
            url = o.getString("url");
            text = o.getString("text");
            image = o.getString("image");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        i.putExtra("url", url);
        i.putExtra("text", text);
        i.putExtra("image", image);
        v.getContext().startActivity(i);


    }
}
