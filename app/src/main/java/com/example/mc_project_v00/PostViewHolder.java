package com.example.mc_project_v00;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "Post_RecyclerView";
    View itemView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }


    static class ViewHolder_Post_Text extends PostViewHolder{
        TextView textViewUsername, textViewContent;
        ImageView imageViewProfileImage;

        public ViewHolder_Post_Text(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Image_Username);
            textViewContent = itemView.findViewById(R.id.post_Image_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Image_ProfileImage));
        }
    }

    static class ViewHolder_Post_Image extends PostViewHolder implements View.OnClickListener {
        TextView textViewUsername;
        ImageView imageViewProfileImage, imageViewContent;
        private View.OnClickListener mClickListener = null;

        public ViewHolder_Post_Image(@NonNull View itemView, View.OnClickListener clicklistener) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            imageViewContent = itemView.findViewById(R.id.post_text_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Text_ProfileImage));

            itemView.findViewById(R.id.post_Image_Content).setOnClickListener(this);
            mClickListener = clicklistener;

        }

        @Override
        public void onClick(View v) {
            Log.v(TAG, "Click on image");

            Intent i = new Intent(CanaleActivity.getPostActivityContext(), ImageActivity.class);
            v.getContext().startActivity(i);

            /*
            String nomeCanale = Model.getInstance().getChannelFromList(position);
            Intent i = new Intent(BachecaActivity.this, CanaleActivity.class);
            i.putExtra("nomeCanale", nomeCanale);
            i.putExtra("position", position);
            startActivity(i);

             */

        }
    }

    static class ViewHolder_Post_Position extends PostViewHolder implements View.OnClickListener {
        TextView textViewUsername;
        ImageView imageViewProfileImage;
        Button settingsButton;
        private View.OnClickListener mListClickListener;

        public ViewHolder_Post_Position(@NonNull View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Position_Username);
            imageViewProfileImage = itemView.findViewById(R.id.post_Position_ProfileImage);
            settingsButton = itemView.findViewById(R.id.post_Position_Button_ShowPosition);

            itemView.findViewById(R.id.post_Position_Button_ShowPosition).setOnClickListener(this);
            mListClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            Log.v(TAG, "Click on position");
        }
    }

}
