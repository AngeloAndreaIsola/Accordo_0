package com.example.mc_project_v00;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Post_Text_ViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "Post_Text_RecyclerView";
    private TextView mTextView = null;

        public Post_Text_ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.post_text);
        }
    }
