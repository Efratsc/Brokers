package com.example.androidprojct;

import com.example.androidprojct.models.Logins;

public class LoginResponse {

    private boolean error;
    private String message; 
    private Logins login;

    public LoginResponse(boolean error, String message, Logins login) {
        this.error = error;
        this.message = message;
        this.login=login;
        ;
    }

    public boolean isError() {

        return error;
    }

    public String getMessage() {
        return message;
    }

    public Logins getUser() {
        return login;
    }
}