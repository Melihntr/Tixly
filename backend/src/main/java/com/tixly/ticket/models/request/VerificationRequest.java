package com.tixly.ticket.models.request;

public class VerificationRequest {
    private String username;
    private String email;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }
}