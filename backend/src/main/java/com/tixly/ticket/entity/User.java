package com.tixly.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.ValidUtil;

import lombok.Data;

@Entity
@Data
public class User {
    private JdbcTemplate jdbcTemplate;
    private JwtUtil jwtUtil;
    private ValidUtil ValidUtil;

    @Id
    private Long id;
    private String username;
    private String password;
    private String mail;
    private String gender;
    private String authKey;
    private String status;
    private String verificationCode;

    public User(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil,  ValidUtil ValidUtil){
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
        this.ValidUtil = ValidUtil;
    }

    public void createCustomer(String username, String password, String mail, String gender) {
        String hashedPassword = HashUtil.sha256(password);
        String sql = "INSERT INTO customer (username, password, mail, gender, auth_key, account_status, verification_code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                jdbcTemplate.update(sql, username, hashedPassword, mail, gender, authKey, "PENDING",null);
                
              
    }

    public void updateVerificationCode(String username, String verificationCode) {
        String sql = "UPDATE customer SET verification_code = ? WHERE username = ?";
        jdbcTemplate.update(sql, verificationCode, username);
    }

    @Transactional
    public boolean deleteCustomerById(Long customerId) {
        // Check if the customer exists
        String selectSql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(selectSql, new Object[]{customerId}, Integer.class);

        if (count != null && count > 0) {
            // Delete the customer
            String deleteSql = "DELETE FROM customer WHERE id = ?";
            jdbcTemplate.update(deleteSql, customerId);
            return true;
        } else {
            return false;
        }
    }

    // Method to verify code with one parameter
    public boolean verifyCode(String verificationCode) {
        String sql = "SELECT COUNT(*) FROM customer WHERE verification_code = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, verificationCode);
        if (count > 0) {
            // Update account_status to "active" for the verified customer
            sql = "UPDATE customer SET account_status = 'active', verification_code = NULL WHERE verification_code = ?";
            jdbcTemplate.update(sql, verificationCode);
            return true; // Verification successful
        }
        return false; // Verification failed
    }

    public void activateCustomer(User customer) {
        customer.setStatus("ACTIVE");

        String updateSql = "UPDATE customer SET account_status = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, customer.getStatus(), customer.getId());
    }


    public void updatePassword(String username, String newPassword) {
        String hashedPassword = HashUtil.sha256(newPassword);
        String sql = "UPDATE customer SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, hashedPassword, username);
    }
    // Method to validate the customer details

    

    public String authenticateCustomer(String username, String password) {
        
        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        // Query to check username and hashed password
        String sql = "SELECT id FROM customer WHERE username = ? AND password = ?";
        Long customerId = jdbcTemplate.queryForObject(sql, new Object[]{username, hashedPassword}, Long.class);

            // Generate JWT token if customer is found
            String token = jwtUtil.generateToken(username);
            updateAuthKey(username, token);
            
        return token;
        
    }
   
    public void logout(String authKey) {
        // Update auth_key to null in the database for the given authKey
        
        String updateSql = "UPDATE customer SET auth_key = null WHERE auth_key = ?";
        jdbcTemplate.update(updateSql, authKey);
    }
    
    public boolean isUserLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }
    public boolean isAuthKeyValid(String authKey) {
        String sql = "SELECT COUNT(*) FROM customer WHERE auth_key = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{authKey}, Integer.class);
        return count != null && count > 0;
    }
    

    public void updateAuthKey(String username, String authKey) {
        String updateSql = "UPDATE customer SET auth_key = ? WHERE username = ?";
        jdbcTemplate.update(updateSql, authKey, username);
    }
    public boolean IsUserExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND mail = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, email}, Integer.class);
        return count != null && count > 0;
    }
    
}
