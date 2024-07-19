package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.ECustomer;

@Service
public class AuthDomainService {
    
    @Autowired
    private EntityService entityService;

    /**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
     public String login(String username, String password) {
        ECustomer customerEntity = entityService.getCustomer();

        String authCode = customerEntity.authenticateCustomer(username, password);

        return authCode;

    }

    /**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
    public void logout(String authKey) {
        ECustomer customerEntity = entityService.getCustomer();

        customerEntity.logout(authKey);
    }
}