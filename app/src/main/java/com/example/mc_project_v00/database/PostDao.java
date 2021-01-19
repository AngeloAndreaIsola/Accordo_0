package com.example.mc_project_v00.database;

import androidx.annotation.IntegerRes;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAllPosts(List<Post> posts);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addPost(Post post);

    @Delete
    void deletePost(Post post);

    @Query("SELECT * FROM posts")
    List<Post> getAllPosts();

    @Update
    void updatePosts(Post... posts);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addContentImage(PostContentImage postContentImage);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addPostProfileImage(PostProfileImage profileImage);

}
