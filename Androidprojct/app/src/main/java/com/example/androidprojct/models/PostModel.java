package com.example.androidprojct.models;

import com.google.gson.annotations.SerializedName;

public class PostModel {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("post_text")
    private String post_text;

    @SerializedName("service_id")
    private int service_id;

    @SerializedName("post_image")
    private String post_image;


    public PostModel(int id, int user_id,String post_text,int service_id, String post_image) {
        this.id = id;
        this.user_id=user_id;
        this.post_text = post_text;
        this.service_id=service_id;

        this.post_image = post_image;

    }

    public int getId() {
        return id;
    }

    public  int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }
}
