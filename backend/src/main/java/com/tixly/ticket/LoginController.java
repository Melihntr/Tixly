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
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.authenticate(loginRequest);
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

            // Perform logout logic
            authService.logout(username);

            // Print "Logout successful" to console
            System.out.println("Logout successful for username: " + username);

            // Return a response
            return ResponseEntity.ok(username + " logged out successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid token format");
        }
    } catch (Exception e) {
        // Log the exception
        System.err.println("Error during logout: " + e.getMessage());
        e.printStackTrace(); // Print stack trace for detailed error analysis

        // Return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error during logout: " + e.getMessage());
    }
}

}