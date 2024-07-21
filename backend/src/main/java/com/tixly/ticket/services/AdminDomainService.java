package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.utils.BearerUtil;

@Service
public class AdminDomainService {

    @Autowired
    private EntityService entityService;


/**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
    public String login(String username, String password) {
        AdminUser userEntity = entityService.getAdmin();
        if(userEntity.isOwnerLoggedIn(username))
        {
            throw new IllegalStateException(username + " is already logged in.");
        }
        
        String authCode = userEntity.authenticateOwner(username, password);

        return authCode;

    }

    /**
     * {@summary}: username password alır, authKey döndürür
     * @param loginRequest
     * @return
     */
    public void logout(String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser userEntity = entityService.getAdmin();
        
        if(!userEntity.isAuthKeyValid(jwtToken))
        {
            throw new IllegalStateException("Invalid session for authKey: " + jwtToken);
        }
        

        userEntity.logoutOwner(jwtToken);
    }
}