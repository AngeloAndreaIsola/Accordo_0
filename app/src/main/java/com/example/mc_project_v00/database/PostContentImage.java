package com.example.mc_project_v00.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Content_Images")
public class PostContentImage {

    @NonNull
    @PrimaryKey
    private String image_pid;

    @ColumnInfo(name = "image_content")
    private String postContentImage;

    public String getImage_pid() {
        return image_pid;
    }

    public void setImage_pid(String image_pid) {
        this.image_pid = image_pid;
    }

    public String getPostContentImage() {
        return postContentImage;
    }

    public void setPostContentImage(String postContentImage) {
        this.postContentImage = postContentImage;
    }
}
