package com.example.mc_project_v00;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    View itemView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    static class ViewHolder_Post_Text extends PostViewHolder{   //Post testo
        TextView textViewUsername, textViewContent;
        ImageView imageViewProfileImage;

        public ViewHolder_Post_Text(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Image_Username);
            textViewContent = itemView.findViewById(R.id.post_Image_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Image_ProfileImage));
        }
    }

    static class ViewHolder_Post_Image extends PostViewHolder{
        TextView textViewUsername;
        ImageView imageViewProfileImage, imageViewContent;

        public ViewHolder_Post_Image(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            imageViewContent = itemView.findViewById(R.id.post_text_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Text_ProfileImage));
        }
    }

    static class ViewHolder_Post_Position extends PostViewHolder{
        TextView textViewUsername;
        ImageView imageViewProfileImage;
        Button settingsButton;

        public ViewHolder_Post_Position(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Position_Username);
            imageViewProfileImage = itemView.findViewById(R.id.post_Position_ProfileImage);
            settingsButton = itemView.findViewById(R.id.post_Position_Button_ShowPosition);
        }
    }

}
