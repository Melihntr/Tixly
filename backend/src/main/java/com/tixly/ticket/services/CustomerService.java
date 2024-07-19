package com.tixly.ticket.services;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.repo.CustomerRowMapper;
import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.RuleBase;
import com.tixly.ticket.utils.ValidUtil;
@Service
public class CustomerService {

    private String storedCode;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private JavaMailSender javaMailSender;

    

    

    

    public void createCustomer(Customer customer) {
        String hashedPassword = HashUtil.sha256(customer.getPassword());
        customer.setPassword(hashedPassword);
        String sql = "INSERT INTO customer (username, password, mail, gender, auth_key, account_status, verification_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Generate verification code
        
        customer.setAuthKey(jwtUtil.generateToken(customer.getUsername()));
        jdbcTemplate.update(sql, customer.getUsername(), customer.getPassword(),
                customer.getmail(), customer.getGender(), customer.getAuthKey(),
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

    // Method to generate a 6-digit random verification code
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        storedCode = String.valueOf(code); // Store the generated code
        return storedCode;
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

    
    public void activateCustomer(Customer customer) {
        customer.setStatus("ACTIVE");

        String updateSql = "UPDATE customer SET account_status = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, customer.getStatus(), customer.getId());
    }
     public Customer getCustomerByVerificationCode(String verificationCode) {
        String sql = "SELECT * FROM customer WHERE verification_code = ?";
        List<Customer> customers = jdbcTemplate.query(sql, new Object[]{verificationCode}, new CustomerRowMapper());
        
        if (!customers.isEmpty()) {
            return customers.get(0); // Assuming verification code is unique, return the first match
        } else {
            return null; // No customer found with the given verification code
        }
    }
    public Customer findByUsername(String username) {
        String sql = "SELECT * FROM customer WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new CustomerRowMapper());
    }
    public void updatePassword(String username, String newPassword) {
        String hashedPassword = HashUtil.sha256(newPassword);
        String sql = "UPDATE customer SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, hashedPassword, username);
    }
// Method to send verification code via email
    public void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + code);
        javaMailSender.send(message);
    }
    
}
*/


