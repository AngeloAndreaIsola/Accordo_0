package com.example.mc_project_v00.snipets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mc_project_v00.R;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater mInflater = null;
    private OnListClickListener mListClickListener = null;

    public Adapter (Context context, OnListClickListener listClickListener){
        mInflater = LayoutInflater.from(context);
        mListClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //viene chiamato quando dobbiamo creare una nuova view
        View view = mInflater.inflate(R.layout.single_row, parent, false);
        return new ViewHolder(view, mListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //viene chiamato quando è necessario riciclare una view
        String s = Model.getInstance().getContact(position);
        holder.updateContent(s);

    }

    @Override
    public int getItemCount() {  //dice quanti sono gli elementi ella lista
        return Model.getInstance().getContactSize();
    }
}
