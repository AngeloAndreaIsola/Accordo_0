package com.example.mc_project_v00;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public class Post_Image_ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "Post_Image_RecyclerView";
        TextView textViewUsername;
        ImageView imageViewProfileImage, imageViewContent;

        public Post_Image_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            imageViewContent = itemView.findViewById(R.id.post_Image_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Position_ProfileImage));
        }
    }

    public class Post_Text_ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "Post_Text_RecyclerView";
        private TextView textViewUsername, textViewContent;
        private ImageView imageViewProfileImage;

        public Post_Text_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            textViewContent = itemView.findViewById(R.id.post_text_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Position_ProfileImage));
        }
    }

    public class Post_Position_ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "Post_Position_RecyclerView";
        private TextView mTextView = null;

        public Post_Position_ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
