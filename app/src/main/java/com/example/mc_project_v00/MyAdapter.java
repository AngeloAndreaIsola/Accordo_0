package com.example.mc_project_v00;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>  {
    private LayoutInflater mInflater = null;
    private OnListClickListener mListClickListener = null;

    public MyAdapter (Context context, OnListClickListener listClickListener){
        mInflater = LayoutInflater.from(context);
        mListClickListener = listClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //viene chiamato quando dobbiamo creare una nuova view
        View view = mInflater.inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(view, mListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { //viene chiamato quando è necessario riciclare una view
        String s = null;
        try {
            s = Model.getInstance().getChannelFromList(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.updateContent(s);

    }

    @Override
    public int getItemCount() {  //dice quanti sono gli elementi ella lista
        return Model.getInstance().getChannelListSize();
    }

}
