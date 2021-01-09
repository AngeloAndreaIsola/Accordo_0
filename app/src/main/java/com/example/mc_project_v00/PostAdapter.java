package com.example.mc_project_v00;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter {
    private static final String TAG ="Post RecyclerView";
    List<JSONObject> postList;
    Context contextContainer;
    ComunicationController ccPostAdapter;

    public int getItemViewType(int position){
        try {
            if(postList.get(position).getString("type").contains("t")){
                return 0;
            }else if (postList.get(position).getString("type").contains("i")) {
                return 1;
            }else{
                return 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 3;
    }

    public PostAdapter (JSONObject object, Context context) throws JSONException {
        List<JSONObject> tempPostList = new ArrayList<JSONObject>();;
        JSONArray postArray = object.getJSONArray("posts");
        for(int i=0; i < postArray.length(); i++ ){
            tempPostList.add((JSONObject) postArray.get(i));
        }

        this.postList = tempPostList;

        this.contextContainer = context;

        this.ccPostAdapter = new ComunicationController(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextContainer);
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.post_text, parent, false);
            return new ViewHolder_Post_Text(view);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.post_image, parent, false);
            return new ViewHolder_Post_Image(view);
        } else { //if (viewType == 2)
            view = layoutInflater.inflate(R.layout.post_position, parent, false);
            return new ViewHolder_Post_Position(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //quello di youtube
        try {
            if(postList.get(position).getString("type").contains("t")){ //BIND VIEWHOLDERONE

                ViewHolder_Post_Text viewHolderPostText = (ViewHolder_Post_Text) holder;

                TextView username = viewHolderPostText.itemView.findViewById(R.id.post_Text_Username);
                JSONObject object_username = postList.get(position);
                String temp_username = object_username.getString("name");
                username.setText(temp_username);


                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE PROFILO
                ImageView profileImage = viewHolderPostText.itemView.findViewById(R.id.post_Text_ProfileImage);
                String uid = postList.get(position).getString("uid");
                String sid = null;
                //ccPostAdapter.getUserPicture();
                //profileImage.setImageBitmap();

                TextView content = viewHolderPostText.itemView.findViewById(R.id.post_text_Content);
                JSONObject object_content = postList.get(position);
                String temp_content = object_content.getString("name");
                content.setText(temp_content);

            }else if(postList.get(position).getString("type").contains("i")) { //BIND VIEWHOLDERONE

                ViewHolder_Post_Image viewHolderPostImage = (ViewHolder_Post_Image) holder;

                TextView username = viewHolderPostImage.itemView.findViewById(R.id.post_Image_Username);
                JSONObject object_username = postList.get(position);
                String temp_username = object_username.getString("name");
                username.setText(temp_username);

                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE PROFILO
                ImageView profileImage = viewHolderPostImage.itemView.findViewById(R.id.post_Image_ProfileImage);
                //profileImage.setImageBitmap();

                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE CONTENUTO
                /*
                TextView content = viewHolderPostImage.itemView.findViewById(R.id.post_Image_Content);
                JSONObject object_content = postList.get(position);
                String temp_content = object_content.getString("content");
                content.setText(temp_content);

                 */

                /*
                ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
                viewHolderTwo.textViewUsername.setText("federico");
                viewHolderTwo.imageViewProfileImage.setImageBitmap();
                viewHolderTwo.imageViewContent.setImageBitmap();

                 */

            }else if(postList.get(position).getString("type").contains("l")){
                /*
                ViewHolderThree viewHolderThree = (ViewHolderThree) holder;
                viewHolderThree.textViewUsername.setText("federico");

                 */
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder_Post_Text extends RecyclerView.ViewHolder{   //Post testo
        TextView textViewUsername, textViewContent;
        ImageView imageViewProfileImage;

        public ViewHolder_Post_Text(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Image_Username);
            textViewContent = itemView.findViewById(R.id.post_Image_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Image_ProfileImage));
        }
    }

    class ViewHolder_Post_Image extends RecyclerView.ViewHolder{
        TextView textViewUsername;
        ImageView imageViewProfileImage, imageViewContent;

        public ViewHolder_Post_Image(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            imageViewContent = itemView.findViewById(R.id.post_text_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Text_ProfileImage));
        }
    }

    class ViewHolder_Post_Position extends RecyclerView.ViewHolder{
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
