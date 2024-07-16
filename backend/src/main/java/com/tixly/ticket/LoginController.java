package com.tixly.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;


@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username.length() < 4 || username.length() > 50 || password.length() < 4 || password.length() > 50) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        if (customer != null) {
            // Generate a unique auth_key for this login
            String authKey = generateUniqueAuthKey(username);

            // Update the auth_key in the database
            updateAuthKey(customer.getId(), authKey);

            // Print "Login successful" to console
            System.out.println("Login successful for username: " + username);

            // Return the authKey in the response along with "Login successful"
            return ResponseEntity.ok(new LoginResponse(authKey, "Login successful. Hello "+username));
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    private String generateUniqueAuthKey(String username) {
        // Generate a unique auth_key based on username and current timestamp or random value
        // Example: Using SHA-256 hash of username and current timestamp
        String uniqueValue = username + System.currentTimeMillis();
        return HashUtil.sha256(uniqueValue);
    }

    private void updateAuthKey(Long customerId, String authKey) {
        String updateSql = "UPDATE customer SET auth_key = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, authKey, customerId);
    }
}