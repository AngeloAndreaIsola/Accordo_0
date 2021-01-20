package com.example.mc_project_v00;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import com.android.volley.VolleyError;
import com.example.mc_project_v00.database.AppExecutors;
import com.example.mc_project_v00.database.DatabaseClient;
import com.example.mc_project_v00.database.PostContentImage;
import com.example.mc_project_v00.database.PostProfileImage;
import com.example.mc_project_v00.database.PostRoomDatabase;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private static final String TAG ="Post RecyclerView";
    private List<JSONObject> postList;   //TODO: INVERTI LA LISTA ALTRIMENTI SI VEDONO IN ALTO GLI ULTIMI MESSSAGGI
    private Context contextContainer;
    private ComunicationController ccPostAdapter;
    private String sid;
    private View.OnClickListener mClickListener = null;
    private PostRoomDatabase postRoomDatabase;



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
            return 3;
        }

    }

    public PostAdapter (JSONObject object, Context context, String sid, View.OnClickListener ClickListener) throws JSONException {
        List<JSONObject> tempPostList = new ArrayList<JSONObject>();;
        JSONArray postArray = object.getJSONArray("posts");
        for(int i=0; i < postArray.length(); i++ ){
            tempPostList.add((JSONObject) postArray.get(i));
        }

        this.postList = tempPostList;

        this.contextContainer = context;

        this.ccPostAdapter = new ComunicationController(context);

        this.sid = sid;

        mClickListener = ClickListener;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextContainer);
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.post_text, parent, false);
            return new PostViewHolder(view);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.post_image, parent, false);
            return new PostViewHolder(view);
        } else { //if (viewType == 2)
            view = layoutInflater.inflate(R.layout.post_position, parent, false);
            return new PostViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) { //TODO: CONTROLLA CHE RECICLI
        try {
            if(postList.get(position).getString("type").contains("t")){ //BIND VIEWHOLDERONE

                PostViewHolder.ViewHolder_Post_Text viewHolderPostText = new PostViewHolder.ViewHolder_Post_Text(holder.itemView);

                //TEXT SET USERNAME
                setUsername(viewHolderPostText, R.id.post_Text_Username, position);

                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE PROFILO
                if (postList.get(position).getInt("pversion")==0){
                    ImageView profilePicture = viewHolderPostText.itemView.findViewById(R.id.post_Text_ProfileImage);
                    profilePicture.setImageResource(R.drawable.ic_baseline_account_box_24);

                    //viewHolderPostText.imageViewProfileImage.setImageResource(R.drawable.ic_baseline_account_box_24);
                }else {
                    profileImageRequest(position, viewHolderPostText, R.id.post_Text_ProfileImage);
                }


                //TEXT SET CONTENT
                TextView content = viewHolderPostText.itemView.findViewById(R.id.post_text_Content);
                JSONObject object_content = postList.get(position);
                String temp_content = object_content.getString("content");
                content.setText(temp_content);

            }else if(postList.get(position).getString("type").contains("i")) { //BIND VIEWHOLDERONE

                PostViewHolder.ViewHolder_Post_Image viewHolderPostImage = new PostViewHolder.ViewHolder_Post_Image(holder.itemView, mClickListener, position);

                //IMAGE SE USERNAME
                setUsername(viewHolderPostImage, R.id.post_Image_Username, position);

                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE PROFILO
                //profileImageRequest(position, viewHolderPostImage, R.id.post_Image_ProfileImage );
                if (postList.get(position).getInt("pversion")==0){
                    ImageView profilePicture = viewHolderPostImage.itemView.findViewById(R.id.post_Image_ProfileImage);
                    profilePicture.setImageResource(R.drawable.ic_baseline_account_box_24);

                    //viewHolderPostImage.imageViewContent.setImageResource(R.drawable.ic_baseline_account_box_24);
                }else {
                    profileImageRequest(position, viewHolderPostImage, R.id.post_Image_ProfileImage);
                }

                //FAI LA CHIAMATA PER PRENDERE L'IMMAGINE CONTENUTO
                String pid = postList.get(position).getString("pid");

                ccPostAdapter.getPostImage(sid, pid, response -> {
                    try {
                        handleGetPostImageResponse(response, viewHolderPostImage, position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> reportErrorToUser(error));


            }else if(postList.get(position).getString("type").contains("l")){

                PostViewHolder.ViewHolder_Post_Position viewHolderPostPosition = new PostViewHolder.ViewHolder_Post_Position(holder.itemView, mClickListener, position);

                setUsername(viewHolderPostPosition, R.id.post_Position_Username, position);

                //profileImageRequest(position, viewHolderPostPosition, R.id.post_Position_ProfileImage);
                if (postList.get(position).getInt("pversion")==0){
                    ImageView profilePicture = viewHolderPostPosition.itemView.findViewById(R.id.post_Position_ProfileImage);
                    profilePicture.setImageResource(R.drawable.ic_baseline_account_box_24);

                    //viewHolderPostPosition.imageViewProfileImage.setImageResource(R.drawable.ic_baseline_account_box_24);
                }else {
                    profileImageRequest(position, viewHolderPostPosition, R.id.post_Position_ProfileImage);
                }

                //SET CONTENT
                Button showPositionButton = viewHolderPostPosition.itemView.findViewById(R.id.post_Position_Button_ShowPosition);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void handleGetPostImageResponse(JSONObject response, PostViewHolder.ViewHolder_Post_Image viewHolderPostImage, int position ) throws JSONException {
        Log.d(TAG, "request post image correct: "+ response.toString());

        //decodifica da stringa a bitmap
        String encodedImage = response.getString("content");
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView content = viewHolderPostImage.itemView.findViewById(R.id.post_Image_Content);
        content.setImageBitmap(decodedByte);

        //TODO: HANDLE BAD BASE64

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PostRoomDatabase postRoomDatabase = DatabaseClient.getInstance(contextContainer).getPostRoomDatabase();

                PostContentImage postContentImage = new PostContentImage();
                try {
                    postContentImage.setPid(response.getString("pid"));
                    postContentImage.setPostContentImage(response.getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                postRoomDatabase.postDao().addContentImage(postContentImage);
                Log.d(TAG, "immagine post salvata nel database");
            }
        });

    }
    private void handleGetUSerPictureResponse(JSONObject response, PostViewHolder postViewHolder, int viewID) throws JSONException {
        Log.d(TAG, "request user picture correct: "+ response.toString());

        //decodifica da stringa a bitmap
        String encodedImage = response.getString("picture");
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PostRoomDatabase postRoomDatabase = DatabaseClient.getInstance(contextContainer).getPostRoomDatabase();

                PostProfileImage postProfileImage = new PostProfileImage();
                try {
                    postProfileImage.setUid(response.getString("uid"));
                    postProfileImage.setProfileImage(response.getString("picture"));
                    postProfileImage.setVersion(response.getInt("pversion"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                postRoomDatabase.postDao().addPostProfileImage(postProfileImage);
                Log.d(TAG, "immagine proilo salvata nel database");
            }
        });

            ImageView profilePicture = postViewHolder.itemView.findViewById(viewID);
            profilePicture.setImageBitmap(decodedByte);


    }

    private void reportErrorToUser(VolleyError error) {
        Log.d(TAG, "request error: " + error.toString());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void profileImageRequest(int position, PostViewHolder postViewHolder, int viewID) throws JSONException {
        String uid = postList.get(position).getString("uid");

        ccPostAdapter.getUserPicture(sid, uid, response -> {
            try {
                handleGetUSerPictureResponse(response,  postViewHolder, viewID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> reportErrorToUser(error));

    }

    public void setUsername(PostViewHolder postViewHolder, int viewID, int position) throws JSONException {
        TextView username = postViewHolder.itemView.findViewById(viewID);
        JSONObject object_username = postList.get(position);
        String temp_username = object_username.getString("name");
        username.setText(temp_username);
    }


}
