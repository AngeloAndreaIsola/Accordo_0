package com.example.mc_project_v00.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Content Images")
public class PostContentImage {

    @NonNull
    @PrimaryKey
    private String pid;

    @ColumnInfo(name = "image content")
    private String postContentImage;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPostContentImage() {
        return postContentImage;
    }

    public void setPostContentImage(String postContentImage) {
        this.postContentImage = postContentImage;
    }
}
