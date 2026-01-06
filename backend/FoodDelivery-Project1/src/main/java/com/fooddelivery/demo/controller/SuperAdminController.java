package com.fooddelivery.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super-admin")
@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")

public class SuperAdminController {

    @GetMapping("/test")
    public String test() {
        return "SUPER ADMIN ACCESS GRANTED";
    }
}