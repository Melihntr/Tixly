package com.tixly.ticket.models.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String mail;
    private String gender;

}