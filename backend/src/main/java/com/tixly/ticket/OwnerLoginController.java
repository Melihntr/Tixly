package com.tixly.ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.services.AuthService;
import com.tixly.ticket.utils.JwtUtil;

@RestController
@RequestMapping("/owner")
public class OwnerLoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (authService.isOwnerLoggedIn(loginRequest.getUsername())) {
            return ResponseEntity.ok("User is already logged in.");
        }

        LoginResponse response = authService.authenticateOwner(loginRequest);
        if (response.getAuthKey() != null) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(response.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                // Extract JWT token from Authorization header
                String jwtToken = token.substring(7); // Remove "Bearer " prefix
                // Extract username from JWT token
                String username = jwtUtil.extractUsername(jwtToken);
    
                // Check if the user is already logged out
                if (!authService.isOwnerLoggedIn(username)) {
                    return ResponseEntity.badRequest().body("No valid session available for user: " + username);
                }
    
                authService.logoutOwner(username);
                System.out.println("Logout successful for username: " + username);
                return ResponseEntity.ok(username + " logged out successfully.");
            } else {
                return ResponseEntity.badRequest().body("Invalid token format");
            }
        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during logout: " + e.getMessage());
        }
    }
}
