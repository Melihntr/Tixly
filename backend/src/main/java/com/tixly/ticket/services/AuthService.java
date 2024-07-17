package com.tixly.ticket.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.entity.Owner;
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

    // Existing authenticate method for customer
    public LoginResponse authenticateCustomer(LoginRequest loginRequest) {
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

            // Update auth_key in the database
            updateAuthKeyForCustomer(customer.getId(), jwtToken);

            // Return the JWT token in the response along with "Login successful"
            return new LoginResponse(jwtToken, "Login successful. Hello " + username);
        } else {
            return new LoginResponse(null, "Invalid username or password");
        }
    }

    // New authenticate method for owner
    public LoginResponse authenticateOwner(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate username and password length
        if (username.length() < 4 || username.length() > 50 || password.length() < 4 || password.length() > 50) {
            return new LoginResponse(null, "Invalid username or password");
        }

        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        String sql = "SELECT id, username, companyid FROM owner WHERE username = ? AND password = ?";
        Owner owner = null;
        try {
            owner = jdbcTemplate.queryForObject(sql, new Object[]{username, hashedPassword},
                    (rs, rowNum) -> {
                        Owner own = new Owner();
                        own.setId(rs.getLong("id"));
                        own.setUsername(rs.getString("username"));
                        own.setCompanyid(rs.getString("companyid"));
                        return own;
                    });
        } catch (EmptyResultDataAccessException e) {
            return new LoginResponse(null, "Invalid username or password");
        }

        if (owner != null) {
            // Generate JWT token
            String jwtToken = jwtUtil.generateToken(username);

            // Update auth_key in the database
            updateAuthKeyForOwner(owner.getId(), jwtToken);

            // Return the JWT token in the response along with "Login successful"
            return new LoginResponse(jwtToken, "Login successful. Hello " + username);
        } else {
            return new LoginResponse(null, "Invalid username or password");
        }
    }

    @Transactional
    public void logout(String username) {
        // Update auth_key to null in the database for the user (customer)
        String updateSql = "UPDATE customer SET auth_key = null WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
    }

    @Transactional
    public void logoutOwner(String username) {
        // Update auth_key to null in the database for the user (owner)
        String updateSql = "UPDATE owner SET auth_key = null WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
    }

    public boolean isUserLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public boolean isOwnerLoggedIn(String username) {
        String sql = "SELECT COUNT(*) FROM owner WHERE username = ? AND auth_key IS NOT NULL";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    private void updateAuthKeyForCustomer(Long customerId, String authKey) {
        String updateSql = "UPDATE customer SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, customerId);
    }

    private void updateAuthKeyForOwner(Long ownerId, String authKey) {
        String updateSql = "UPDATE owner SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, ownerId);
    }
}