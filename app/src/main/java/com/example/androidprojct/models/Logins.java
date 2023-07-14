package com.example.androidprojct.models;

import com.google.gson.annotations.SerializedName;

public class Logins {
    @SerializedName("username")
    private String username;
    private int user_id;
    @SerializedName("password")
    private String password;

    public Logins(int user_id, String username, String password) {
        this.user_id=user_id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }
}
