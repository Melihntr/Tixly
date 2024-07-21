package com.tixly.ticket.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    
    private final Key secretKey;

    // Constructor to generate the secret key
    public JwtUtil() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[RuleBase.SECRET_KEY_LENGTH];
        secureRandom.nextBytes(keyBytes);
        this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // Method to generate a JWT token
    public String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(nowMillis))
                .signWith(secretKey)
                .compact();
    }

    // Method to extract username from JWT token
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Method to validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}