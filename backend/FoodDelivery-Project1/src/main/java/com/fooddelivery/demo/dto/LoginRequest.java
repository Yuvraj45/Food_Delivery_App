package com.fooddelivery.demo.dto;

public class LoginRequest {

    private String email;
    private String password;

    // ✅ REQUIRED GETTERS
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // (Optional but safe)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}