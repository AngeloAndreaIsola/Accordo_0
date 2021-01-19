package com.example.mc_project_v00.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private PostRoomDatabase postRoomDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        postRoomDatabase = Room.databaseBuilder(mCtx, PostRoomDatabase.class, "MyToDos").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public PostRoomDatabase getPostRoomDatabase() {
        return postRoomDatabase;
    }
}
