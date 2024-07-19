package com.tixly.ticket.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Entity;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.RuleBase;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.utils.HashUtil;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.RuleBase;
import com.tixly.ticket.utils.ValidUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Owner {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    private Long id;
    private String username;
    private String password;
    private String companyid;
    private String authKey;

  // New authenticate method for owner
    public LoginResponse authenticateOwner(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Validate username and password length
        if (username.length() < RuleBase.MIN_USERNAME_LENGTH || username.length() > RuleBase.MAX_USERNAME_LENGTH || password.length() < RuleBase.MIN_PASSWORD_LENGTH || password.length() > RuleBase.MAX_PASSWORD_LENGTH) {
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
    public void logoutOwner(String username) {
        // Update auth_key to null in the database for the user (owner)
        String updateSql = "UPDATE owner SET auth_key = null WHERE username = ?";
        jdbcTemplate.update(updateSql, username);
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

}
