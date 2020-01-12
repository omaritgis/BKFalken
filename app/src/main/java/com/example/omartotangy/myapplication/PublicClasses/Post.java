package com.example.omartotangy.myapplication.PublicClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Post {
    private HashMap<String,HashMap<String,String>> parentMessanger;
    private HashMap<String,String> messageNumberAndMessage;

    Post(){

    }

    Post(HashMap<String,String> messageNumberAndMessage){
        this.messageNumberAndMessage = messageNumberAndMessage;
        parentMessanger = new HashMap<>();
    }

    public void setNewPost(String numberOfPost, String message){
        int numberOfPostInteger = Integer.parseInt(numberOfPost);

        if(numberOfPostInteger<4){
            numberOfPostInteger = 0;
            numberOfPost = "" + numberOfPostInteger;
        }else{
            numberOfPostInteger++;
            numberOfPost = "" + numberOfPostInteger;
        }

        messageNumberAndMessage.put(numberOfPost,message);
    }

    public HashMap<String, String> getAllPosts(){
        return messageNumberAndMessage;
    }

    public String getPost(String number){
        return messageNumberAndMessage.get(number);
    }

    public void publish(String userName){
        parentMessanger.put(userName,messageNumberAndMessage);
    }

}
