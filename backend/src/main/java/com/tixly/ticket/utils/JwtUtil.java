package com.tixly.ticket.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {

    // Example secret key (replace with your own secret key in production)
    private static final String SECRET_KEY = "8a76c5b23d041f87e7e66f5e9c5a1a2482b7e3c342e153f00159f9a08f8f5906"; // Replace with your actual secret key

    // Method to generate a JWT token
    public String generateToken(String username) {
        // Current time in milliseconds
        long nowMillis = System.currentTimeMillis();

        // Configure the signing key
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        // Build the JWT token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(nowMillis))
                .signWith(key)
                .compact();
    }

    // Method to extract username from JWT token
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Method to validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}