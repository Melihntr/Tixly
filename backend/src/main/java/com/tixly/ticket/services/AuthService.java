package com.tixly.ticket.services;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.utils.JwtUtil;

@Service
public class AuthService {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JwtUtil jwtUtil;

    private Set<String> tokenBlacklist = new HashSet<>();

    public String authenticate(LoginRequest loginRequest) {
        String jpql = "SELECT c FROM Customer c WHERE c.username = :username";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("username", loginRequest.getUsername());
        
        try {
            Customer customer = (Customer) query.getSingleResult();
            if (customer != null && customer.getPassword().equals(loginRequest.getPassword())) {
                return jwtUtil.generateToken(customer.getUsername());
            }
        } catch (Exception ex) {
            // Handle any exceptions (e.g., NoResultException, NonUniqueResultException)
            ex.printStackTrace();
        }
        
        return null;
    }

    
    public void logout(String username, String token) {
        // Invalidate the token (add to blacklist)
        tokenBlacklist.add(token);
        // Additional logout logic if needed (e.g., clear session)
        System.out.println("User logged out: " + username);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
