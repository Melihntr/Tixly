package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.User;
import com.tixly.ticket.models.dto.UserInfo;
import com.tixly.ticket.utils.BearerUtil;

@Service
public class UserDomainService {

    @Autowired
    private EntityService entityService;

    public UserInfo getUserInfoByAuthKey(String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        User user = entityService.getCustomer();
        if (authKey == null || authKey.isEmpty()) {
            throw new IllegalArgumentException("Invalid authentication key.");
        }
        return user.getUserInfoByAuthKey(jwtToken);
    }
}
