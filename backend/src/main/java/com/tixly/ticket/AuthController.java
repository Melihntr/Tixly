package com.tixly.ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.services.AuthService;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.HashUtil;

public class AuthController {

  /*  @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username.length() < 4 || username.length() > 50 || password.length() < 4 || password.length() > 50) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = HashUtil.sha256(password);

        try {
            Customer customer = authService.authenticate(username, hashedPassword);
            if (customer != null) {
                String authKey = generateAuthKey();
                authService.updateAuthKey(customer.getId(), authKey);
                return ResponseEntity.ok(new LoginResponse(authKey));
            } else {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

     @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = authService.extractUsernameFromToken(token);
            authService.logout(username);
            return ResponseEntity.ok("Bye, " + username);
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }

    private String generateAuthKey() {
        String uuid = UUID.randomUUID().toString();
        return HashUtil.sha256(uuid); // Assuming HashUtil.sha256() returns the SHA-256 hash of the input
    }*/
}