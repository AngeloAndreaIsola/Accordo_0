package com.example.mc_project_v00;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "person")
public class Person {
    @PrimaryKey
    public int uid;

    public String name;
    public int pversion;
    public String picture;

}
