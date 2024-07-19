package com.tixly.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.RuleBase;
import com.tixly.ticket.utils.ValidUtil;

import lombok.Data;

@Entity
@Data
public class ECustomer {
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

    public ECustomer(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil,  ValidUtil ValidUtil){
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
        this.ValidUtil = ValidUtil;
    }

    public void createCustomer(ECustomer customer) {
        String hashedPassword = HashUtil.sha256(this.password);
        this.password = hashedPassword;
        String sql = "INSERT INTO customer (username, password, mail, gender, auth_key, account_status, verification_code) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Generate verification code

        customer.setAuthKey(jwtUtil.generateToken(this.username));
        jdbcTemplate.update(sql, customer.getUsername(), this.password,
                customer.getMail(), customer.getGender(), customer.getAuthKey(),
                "PENDING", null);
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
            sql = "UPDATE customer SET account_status = 'active' WHERE verification_code = ?";
            jdbcTemplate.update(sql, verificationCode);
            return true; // Verification successful
        }
        return false; // Verification failed
    }

    public void activateCustomer(ECustomer customer) {
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

    public boolean isValid(ECustomer customer) {
        return customer.getUsername() != null && customer.getUsername().length() >= RuleBase.MIN_USERNAME_LENGTH && customer.getUsername().length() <= RuleBase.MAX_USERNAME_LENGTH
                && customer.getPassword() != null && customer.getPassword().length() >= RuleBase.MIN_PASSWORD_LENGTH && customer.getPassword().length() <= RuleBase.MAX_PASSWORD_LENGTH
                && customer.getMail() != null && ValidUtil.isEmailValid(customer.getMail())
                && !ValidUtil.doesUsernameOrEmailExist(customer.getUsername(), customer.getMail());
    }

    public String authenticateCustomer(String username, String password) {
        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        String sql = "SELECT authKey FROM customer WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username, hashedPassword}, String.class);
    }

    

    @Transactional
    public void logout(String username) {
        // Update auth_key to null in the database for the user (customer)
        String updateSql = "UPDATE customer SET auth_key = null WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
    }

    
    public boolean isUserLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    

    public void updateAuthKeyForCustomer(Long customerId, String authKey) {
        String updateSql = "UPDATE customer SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, customerId);
    }

    
}
