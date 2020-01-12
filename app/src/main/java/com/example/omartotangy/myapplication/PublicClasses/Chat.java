package com.example.omartotangy.myapplication.PublicClasses;

import java.util.HashMap;

/**
 * @author Omar Totangy
 * This class contains data about a message. The uploader and the message. It uses setters and getters.
 */
public class Chat {
    private String message, uploader;
    private int numberOfMessages;

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public Chat(){

    }

    public Chat(String uploader, String m){
        message = m;
        this.uploader = uploader;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
