package com.tixly.ticket.models.response;

public class LoginResponse {

    private String authKey;
    private String message;

    public LoginResponse(String authKey, String message) {
        this.authKey = authKey;
        this.message = message;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}