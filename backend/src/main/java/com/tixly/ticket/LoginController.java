package com.tixly.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.utils.HashUtil;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username.length() < 4 || username.length() > 50) {
            return new ResponseEntity<>("Username must be between 4 and 50 characters", HttpStatus.BAD_REQUEST);
        }

        if (password.length() < 4 || password.length() > 50) {
            return new ResponseEntity<>("Password must be between 4 and 50 characters", HttpStatus.BAD_REQUEST);
        }

        // Password hashing with SHA-256
        String hashedPassword = HashUtil.sha256(password);

        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username,hashedPassword}, Integer.class);

        if (count != null && count > 0) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}