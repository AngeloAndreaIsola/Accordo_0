package com.example.mc_project_v00;

import android.content.Context;
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
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextContainer);
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.post_text, parent, false);
            return new ViewHolderOne(view);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.post_image, parent, false);
            return new ViewHolderTwo(view);
        } else { //if (viewType == 2)
            view = layoutInflater.inflate(R.layout.post_position, parent, false);
            return new ViewHolderThree(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //quello di youtube
        try {
            if(postList.get(position).getString("type").contains("t")){ //BIND VIEWHOLDERONE
                ViewHolderOne viewHolderOne = (ViewHolderOne) holder;

                TextView username = viewHolderOne.itemView.findViewById(R.id.post_Text_Username);
                username.setText("federico");

                ImageView profileImage = viewHolderOne.itemView.findViewById(R.id.post_Text_ProfileImage);
                //profileImage.setImageBitmap();

                TextView content = viewHolderOne.itemView.findViewById(R.id.post_text_Content);
                content.setText("Ciao");

            }else if(postList.get(position).getString("type").contains("i")) { //BIND VIEWHOLDERONE



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

    class ViewHolderOne extends RecyclerView.ViewHolder{   //Post testo
        TextView textViewUsername, textViewContent;
        ImageView imageViewProfileImage;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Image_Username);
            textViewContent = itemView.findViewById(R.id.post_Image_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Image_ProfileImage));
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder{
        TextView textViewUsername;
        ImageView imageViewProfileImage, imageViewContent;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.post_Text_Username);
            imageViewContent = itemView.findViewById(R.id.post_text_Content);
            imageViewProfileImage = itemView.findViewById((R.id.post_Text_ProfileImage));
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder{
        TextView textViewUsername;
        ImageView imageViewProfileImage;
        Button settingsButton;

        public ViewHolderThree(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.post_Position_Username);
            imageViewProfileImage = itemView.findViewById(R.id.post_Position_ProfileImage);
            settingsButton = itemView.findViewById(R.id.post_Position_Button_ShowPosition);
        }
    }

}
