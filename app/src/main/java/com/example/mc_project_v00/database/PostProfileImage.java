package com.example.mc_project_v00.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Profile_Images")
public class PostProfileImage {

    @NonNull
    @PrimaryKey
    private String profile_uid;


    @ColumnInfo(name = "version")
    private int version;



    @ColumnInfo(name = "Profile Image")
    private String profileImage;

    public String getProfile_uid() {
        return profile_uid;
    }

    public void setProfile_uid(String profile_uid) {
        this.profile_uid = profile_uid;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
