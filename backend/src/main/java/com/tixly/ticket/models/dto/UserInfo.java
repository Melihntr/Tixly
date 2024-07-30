package com.tixly.ticket.models.dto;


import lombok.Data;

@Data
public class UserInfo {
    private String email;
    private String gender;
    private String accountStatus;
    private String phoneNumber;
    private String tcNo;
}
