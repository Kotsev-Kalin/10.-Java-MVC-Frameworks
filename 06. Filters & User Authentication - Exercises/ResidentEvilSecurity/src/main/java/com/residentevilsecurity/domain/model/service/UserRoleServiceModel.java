package com.residentevilsecurity.domain.model.service;

import com.residentevilsecurity.domain.enums.Role;

public class UserRoleServiceModel {
    private String id;
    private Role role;

    public UserRoleServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
