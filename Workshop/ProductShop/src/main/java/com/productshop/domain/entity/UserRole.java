package com.productshop.domain.entity;

import com.productshop.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity implements GrantedAuthority {
    private Role role;

    public UserRole() {
    }

    @Column(name = "role", unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    @Transient
    public String getAuthority() {
        return this.getRole().name();
    }
}
