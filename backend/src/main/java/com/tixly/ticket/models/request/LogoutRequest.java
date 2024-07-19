package com.tixly.ticket.models.request;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.DataLengthException;

public class LogoutRequest {
    
    private String authKey;

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(String authKey) throws Exception {
        if(this.authKey == null || this.authKey.length() < 10)
        {
            throw new DataLengthException("auth key 10 karakterden küçük olamaz");
        }
        this.authKey = authKey;
    }
}
