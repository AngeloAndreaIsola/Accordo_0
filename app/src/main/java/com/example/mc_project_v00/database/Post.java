package com.example.mc_project_v00.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Posts")
public class Post {

    @NonNull
    @PrimaryKey
    private String pid;

    @ColumnInfo(name = "uid")
    private String uid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "version")
    private int version;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "contentTextPost")
    private String contentTextPost;

    @ColumnInfo(name = "lat")
    private String lat;

    @ColumnInfo(name = "lon")
    private String lonN;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentTextPost() {
        return contentTextPost;
    }

    public void setContentTextPost(String contentTextPost) {
        this.contentTextPost = contentTextPost;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLonN() {
        return lonN;
    }

    public void setLonN(String lonN) {
        this.lonN = lonN;
    }
}