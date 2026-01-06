package com.fooddelivery.demo.dto.response;

import com.fooddelivery.demo.model.Role;

public class UserSummaryDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserSummaryDTO(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // getters only (no setters needed)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}