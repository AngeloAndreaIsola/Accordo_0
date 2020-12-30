package com.example.mc_project_v00;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final String TAG = "RecyclerView";
    private boolean firstuse = true;
    private TextView mTextView = null;
    private OnListClickListener mListClickListener = null;

    public MyViewHolder(@NonNull View itemView, OnListClickListener clicklistener) {  //qui gli dico che se clicca su un contatto lo riamndo alal pagina contatto
        super(itemView);
        mTextView = itemView.findViewById(R.id.singleRow);
        itemView.setOnClickListener(this);
        mListClickListener = clicklistener;
    }


    public void updateContent (String text){ //posso chiedere di passarmi anche altre cose come oggetti, nomi e cognomi e settare piu campi
        if (firstuse){
            Log.v(TAG, "First use " + text);
        }else {
            Log.v(TAG, "Recycling: " + mTextView.getText() + "with " + text);
        }
        mTextView.setText(text);
    }

    @Override
    public void onClick(View v) {
        mListClickListener.onListClick(getAdapterPosition());
        Log.v(TAG, "Click on: "+ mTextView.getText());
    }
}
