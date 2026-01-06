package com.fooddelivery.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.demo.dto.AuthResponse;
import com.fooddelivery.demo.dto.LoginRequest;
import com.fooddelivery.demo.dto.RegisterRequest;
import com.fooddelivery.demo.model.Role;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if ("SUPER_ADMIN".equalsIgnoreCase(request.getRole())) {
            return ResponseEntity
                    .status(403)
                    .body("SUPER_ADMIN cannot be registered via API");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        authService.register(user);

        return ResponseEntity.ok("Registered Successfully");
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }
}