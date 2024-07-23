package com.tixly.ticket.models.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String username;
    private String verificationCode;
    private String newPassword;

   
}
