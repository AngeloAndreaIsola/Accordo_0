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

    @Query("SELECT * FROM posts WHERE pid=:pid")
    Post getSinglePost(int pid);

    @Query("SELECT image_content FROM content_images WHERE image_pid=:pid")
    String getContentImage(int pid);

    @Query("SELECT version FROM profile_images WHERE profile_uid=:uid")
    int getProfileVersion(int uid);

    @Query("SELECT `Profile Image` FROM profile_images WHERE profile_uid=:uid")
    String getProfileContent(int uid);

    @Update
    void updatePosts(Post... posts);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addContentImage(PostContentImage postContentImage);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void addPostProfileImage(PostProfileImage profileImage);

}
