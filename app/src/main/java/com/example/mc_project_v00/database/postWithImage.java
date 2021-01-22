package com.example.mc_project_v00.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class postWithImage {
    @Embedded public Post post;
    @Relation(
            parentColumn = "pid",
            entityColumn = "image_pid"
    )
    public PostContentImage contentImage;
}
