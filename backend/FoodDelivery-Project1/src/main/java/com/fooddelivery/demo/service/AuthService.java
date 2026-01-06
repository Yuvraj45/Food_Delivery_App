package com.fooddelivery.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddelivery.demo.dto.AuthResponse;
import com.fooddelivery.demo.model.User;
import com.fooddelivery.demo.repository.UserRepository;
import com.fooddelivery.demo.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ================= REGISTER =================
    public void register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // ================= LOGIN =================
    public AuthResponse login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setRole(user.getRole().name()); // ⭐ IMPORTANT

        return response;
    }
}