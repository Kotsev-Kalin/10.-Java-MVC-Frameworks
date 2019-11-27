package com.residentevilsecurity.domain.model.view;


import com.residentevilsecurity.domain.enums.Role;

import java.util.List;

public class UserViewModel {
    private String id;
    private String username;
    private List<Role> authorities;

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

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }
}
