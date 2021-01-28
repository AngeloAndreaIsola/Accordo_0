package com.example.mc_project_v00;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorViewHolder> {
    private static final String TAG = "SPONSOR ADAPTER";
    private LayoutInflater mInflater = null;
    private View.OnClickListener mClickListener = null;

    public SponsorAdapter(Context context, View.OnClickListener ClickListener){
        mInflater = LayoutInflater.from(context);
        mClickListener = ClickListener;
    }

    @Override
    public SponsorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.sponsor_single, parent, false);
        return new SponsorViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorViewHolder holder, int position) {
        String nomeSponsor = null;
        String testoSponsor = null;
        String imgSponsorString = null;  //TODO:STRINGA O BITMAP
        try {
            JSONObject objSponsor = SponsorModel.getInstance().getSponsorObjFromList(position);

            nomeSponsor = objSponsor.getString("url");
            testoSponsor = objSponsor.getString("text");
            imgSponsorString = objSponsor.getString("image");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.textViewUsername.setText(nomeSponsor);
        holder.textViewContent.setText(testoSponsor);

        try {
            //decodifica da stringa a bitmap
            byte[] decodedString = Base64.decode(imgSponsorString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewProfileImage.setImageBitmap(decodedByte);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "BASE 64 SBAGLIATO");
            holder.imageViewProfileImage.setImageResource(R.drawable.ic_baseline_broken_image_24);
        }
    }

    @Override
    public int getItemCount() {
        return SponsorModel.getInstance().getChannelListSize();
    }
}
