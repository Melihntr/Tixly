package com.tixly.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    @Id
    private Long id;
    private String username;
    private String password;
    private String companyid;
    private String authKey;
    private String mail;
    private String status;
    private String verificationCode;
    private String phoneNumber;

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

    public void updateVerificationCode(String username, String verificationCode) {
        String sql = "UPDATE owner SET verification_code = ? WHERE username = ?";
        jdbcTemplate.update(sql, verificationCode, username);
    }

    public boolean IsOwnerExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM owner WHERE username = ? AND mail = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, email}, Integer.class);
        return count != null && count > 0;
    }

    public boolean verifyCode(String verificationCode) {
        String sql = "SELECT COUNT(*) FROM owner WHERE verification_code = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, verificationCode);
        if (count > 0) {
            return true; // Verification successful
        }
        return false; // Verification failed
    }

    public void updatePassword(String username, String newPassword) {
        String hashedPassword = HashUtil.sha256(newPassword);
        String sql = "UPDATE owner SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, hashedPassword, username);
    }

    public boolean isCompanyIdExist(String username) {
        String sql = "SELECT COUNT(*) FROM owner WHERE username = ? AND companyid IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public Long getUserIdByAuthKey(String authKey) {
        String sql = "SELECT id FROM owner WHERE auth_key = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{authKey}, Long.class);    
    }

    public Long getCompanyIdByUserId(Long id) {
        String sql = "SELECT companyid FROM owner WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class); // Fixed the parameter from authKey to id
    }

}
