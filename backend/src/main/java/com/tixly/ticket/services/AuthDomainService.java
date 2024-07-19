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
        if(customerEntity.isUserLoggedIn(username))
        {
            throw new IllegalStateException(username + " is already logged in.");
        }
        
        String authCode = customerEntity.authenticateCustomer(username, password);

        return authCode;

    }

    /**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
    public void logout(String authKey) {
        if(!authKey.contains("Bearer")){
            throw new IllegalArgumentException();
            }
        String jwtToken=authKey.replaceAll("Bearer ","");
        ECustomer customerEntity = entityService.getCustomer();
        
        if(!customerEntity.isAuthKeyValid(jwtToken))
        {
            throw new IllegalStateException("Invalid session for authKey: " + jwtToken);
        }
        

        customerEntity.logout(jwtToken);
    }
}