package com.tixly.ticket.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public LoginResponse authenticate(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate username and password length
        if (username.length() < 4 || username.length() > 50 || password.length() < 4 || password.length() > 50) {
            return new LoginResponse(null, "Invalid username or password");
        }

        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        String sql = "SELECT id, username FROM customer WHERE username = ? AND password = ?";
        Customer customer = null;
        try {
            customer = jdbcTemplate.queryForObject(sql, new Object[]{username, hashedPassword},
                    (rs, rowNum) -> {
                        Customer cust = new Customer();
                        cust.setId(rs.getLong("id"));
                        cust.setUsername(rs.getString("username"));
                        return cust;
                    });
        } catch (EmptyResultDataAccessException e) {
            return new LoginResponse(null, "Invalid username or password");
        }

        if (customer != null) {
            // Generate JWT token
            String jwtToken = jwtUtil.generateToken(username);

            // Return the JWT token in the response along with "Login successful"
            return new LoginResponse(jwtToken, "Login successful. Hello " + username);
        } else {
            return new LoginResponse(null, "Invalid username or password");
        }
    }

    @Transactional
    public void logout(String username) {
        // Update auth_key to null in the database for the user
        String updateSql = "UPDATE customer SET auth_key = null WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
    }
    public boolean isUserLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    private String generateUniqueAuthKey(String username) {
        // Generate a unique auth_key based on username and current timestamp or random value
        // Example: Using SHA-256 hash of username and current timestamp
        String uniqueValue = username;// + System.currentTimeMillis()
        return HashUtil.sha256(uniqueValue);
    }

    private void updateAuthKey(Long customerId, String authKey) {
        String updateSql = "UPDATE customer SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, customerId);
    }
}