package com.example.mc_project_v00;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<String> contacts = null;
    private static Model theInstance = null;

    private  Model(){
        contacts = new ArrayList();
    }
    public static synchronized Model getInstance(){
        if(theInstance==null){
            theInstance= new Model();
        }
        return theInstance;
    }


    public void addFakeData(){
        for(int i=0; i<100; i++){
            contacts.add("contact "+ i);
        }
    }

    public int getContactSize(){
        return contacts.size();
    }

    public String getContact(int i){
        return contacts.get(i);
    }
}
