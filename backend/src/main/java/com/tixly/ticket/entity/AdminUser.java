package com.tixly.ticket.entity;

import javax.persistence.Entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.ValidUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdminUser {

    private JdbcTemplate jdbcTemplate;
    private JwtUtil jwtUtil;
    private ValidUtil ValidUtil;

    private Long id;
    private String username;
    private String password;
    private String companyid;
    private String authKey;


    public AdminUser(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil, ValidUtil validUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
        this.ValidUtil = validUtil;
    }

    // New authenticate method for owner
    public String authenticateOwner(String username, String password) {

        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        // Query to check username and hashed password
        String sql = "SELECT id FROM owner WHERE username = ? AND password = ?";
        Long ownerId = jdbcTemplate.queryForObject(sql, new Object[]{username, hashedPassword}, Long.class);

        // Generate JWT token if customer is found
        String token = jwtUtil.generateToken(username);
        updateAuthKey(username, token);

        return token;

    }

    @Transactional
    public void logoutOwner(String authKey) {
        // Update auth_key to null in the database for the user (owner)
        String updateSql = "UPDATE owner SET auth_key = null WHERE auth_key = ?";
        jdbcTemplate.update(updateSql, authKey);
    }

    public boolean isOwnerLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM owner WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    private void updateAuthKeyForOwner(Long ownerId, String authKey) {
        String updateSql = "UPDATE owner SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, ownerId);
    }

    public boolean isAuthKeyValid(String authKey) {
        String sql = "SELECT COUNT(*) FROM owner WHERE auth_key = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{authKey}, Integer.class);
        return count != null && count > 0;
    }

    public void updateAuthKey(String username, String authKey) {
        String updateSql = "UPDATE owner SET auth_key = ? WHERE username = ?";
        jdbcTemplate.update(updateSql, authKey, username);
    }

}
