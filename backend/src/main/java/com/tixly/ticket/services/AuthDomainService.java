package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.User;
import com.tixly.ticket.utils.BearerUtil;


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
        User userEntity = entityService.getCustomer();
        if(userEntity.isUserLoggedIn(username))
        {
            throw new IllegalStateException(username + " is already logged in.");
        }
        
        String authCode = userEntity.authenticateCustomer(username, password);

        return authCode;

    }

    /**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
    public void logout(String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        User customerEntity = entityService.getCustomer();
        
        if(!customerEntity.isAuthKeyValid(jwtToken))
        {
            throw new IllegalStateException("Invalid session for authKey: " + jwtToken);
        }
        

        customerEntity.logout(jwtToken);
    }
}