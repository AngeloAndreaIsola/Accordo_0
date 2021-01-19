package com.example.mc_project_v00.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Post.class, PostProfileImage.class, PostContentImage.class}, version = 1, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase{
    public abstract PostDao postDao();

    //TODO: FAI IN MODO CHE SIA SINGLETON MAGARI

}

