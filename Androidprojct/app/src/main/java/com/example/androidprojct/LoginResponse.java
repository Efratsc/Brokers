package com.example.androidprojct;

import com.example.androidprojct.models.Login;
import com.example.androidprojct.models.User;

public class LoginResponse {

    private boolean error;
    private String message;
    private Login login;

    public LoginResponse(boolean error, String message, Login login) {
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

    public Login getUser() {
        return login;
    }
}