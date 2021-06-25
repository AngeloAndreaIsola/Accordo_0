package com.example.mc_project_v00;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BachecaAdapter extends RecyclerView.Adapter<BachecaViewHolder>  {
    private LayoutInflater mInflater = null;
    private OnListClickListener mListClickListener = null;
    private List<JSONObject> channelList, tempChannelList;
    private List<JSONObject> filteredChannelList;

    public BachecaAdapter(Context context, OnListClickListener listClickListener){
        mInflater = LayoutInflater.from(context);
        mListClickListener = listClickListener;
        channelList = BachecaModel.getInstance().getChannelList();
    }

    public void filterOn() throws JSONException {
        tempChannelList = channelList;
        for (int i=0; i<channelList.size(); i++){
            if (channelList.get(i).getString("preferred").contains("t")){
                filteredChannelList.add(channelList.get(i));
            }
        }

        channelList.clear();
        channelList = filteredChannelList;
        notifyDataSetChanged();

    }

    public void filterOff(){
        channelList = tempChannelList;
        notifyDataSetChanged();
    }

    public int getItemViewType(int position){
        try {
            if(channelList.get(position).getString("preferred").contains("t")){
                return 0;
            }else{
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 3;
        }

    }


    @Override
    public BachecaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //viene chiamato quando dobbiamo creare una nuova view
        View view = mInflater.inflate(R.layout.single_row, parent, false);

        if (viewType == 0){
            view = mInflater.inflate(R.layout.single_row_prefered, parent, false);
            return new BachecaViewHolder(view, mListClickListener);
        }else{
            return new BachecaViewHolder(view, mListClickListener);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull BachecaViewHolder holder, int position) { //viene chiamato quando è necessario riciclare una view
        String s = null;
        try {
            s = BachecaModel.getInstance().getChannelFromList(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.updateContent(s);

    }

    @Override
    public int getItemCount() {  //dice quanti sono gli elementi ella lista
        return BachecaModel.getInstance().getChannelListSize();
    }

}
