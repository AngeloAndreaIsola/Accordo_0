package com.example.mc_project_v00;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public class Post_Image_ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "Post_RecyclerView";
        private String mImageView = null;

        public Post_Image_ViewHolder(@NonNull View itemView) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.post_text);
            return;
        }
    }

    public class Post_Text_ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "Post_Text_RecyclerView";
        private TextView mTextView = null;

        public Post_Text_ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.post_text);
        }
    }


}
