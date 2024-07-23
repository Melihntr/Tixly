package com.tixly.ticket.models.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest {

    private String username;
    private String mail;

}