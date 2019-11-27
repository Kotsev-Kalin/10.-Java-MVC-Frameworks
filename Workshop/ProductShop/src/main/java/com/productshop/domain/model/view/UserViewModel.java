package com.productshop.domain.model.view;

import com.productshop.domain.enums.Role;

import java.util.List;

public class UserViewModel {
    private String id;
    private String username;
    private String email;
    private List<Role> authorities;
    private List<String> rolesAsStrings;

    public UserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public List<String> getRolesAsStrings() {
        return rolesAsStrings;
    }

    public void setRolesAsStrings(List<String> rolesAsStrings) {
        this.rolesAsStrings = rolesAsStrings;
    }
}
