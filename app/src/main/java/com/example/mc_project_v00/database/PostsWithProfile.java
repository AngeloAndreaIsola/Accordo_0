package com.example.mc_project_v00.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PostsWithProfile {
    @Embedded
    public PostProfileImage profileImage;
    @Relation(
            parentColumn = "profile_uid",
            entityColumn = "uid"
    )

    public List<Post> posts;
}
