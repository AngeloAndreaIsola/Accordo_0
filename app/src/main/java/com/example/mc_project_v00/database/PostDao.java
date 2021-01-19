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
    void insertAll(List<Post> posts);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void add(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM posts")
    List<Post> getAll();

    @Update
    void updateUsers(Post... posts);

}
