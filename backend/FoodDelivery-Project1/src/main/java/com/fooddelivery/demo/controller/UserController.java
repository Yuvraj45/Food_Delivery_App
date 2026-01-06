package com.fooddelivery.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")

public class UserController {

    @GetMapping("/profile")
    public String profile() {
        return "USER profile accessed";
    }
}