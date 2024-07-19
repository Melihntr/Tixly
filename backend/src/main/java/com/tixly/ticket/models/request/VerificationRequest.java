package com.tixly.ticket.models.request;

public class VerificationRequest {

    private String username;
    private String mail;

    // Constructors
    public VerificationRequest() {
    }

    public VerificationRequest(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}