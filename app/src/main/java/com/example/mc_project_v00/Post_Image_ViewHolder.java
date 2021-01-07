package com.example.mc_project_v00;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Post_Image_ViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "Post_RecyclerView";
    private String mImageView = null;

    public Post_Image_ViewHolder(@NonNull View itemView) {
        super(itemView);
        //mImageView = itemView.findViewById(R.id.post_text);
        return;
    }
}

