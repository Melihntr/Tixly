package com.tixly.ticket.models.request;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.DataLengthException;

import com.tixly.ticket.utils.RuleBase;

import lombok.Data;

@Data
public class LogoutRequest {
    
    private String authKey;

    

    public void setAuthKey(String authKey) throws Exception {
        if(this.authKey == null || this.authKey.length() < RuleBase.MIN_AUTHKEY_LENGTH)
        {
            throw new DataLengthException("auth key 10 karakterden küçük olamaz");
        }
        this.authKey = authKey;
    }
}
