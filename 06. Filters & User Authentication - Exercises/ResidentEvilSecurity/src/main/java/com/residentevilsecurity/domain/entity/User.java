package com.residentevilsecurity.domain.entity;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String password;
    private String email;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Collection<? extends UserRole> authorities;

    public User() { }

    @Override
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Collection<? extends UserRole> getAuthorities() {
        return this.authorities;
    }

    @Override
    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return this.username;
    }

    @Override
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @Override
    @Column(name = "account_non_expired")
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Column(name = "account_non_locked")
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Column(name = "credentials_non_expired")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Column(name = "enabled")
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = true;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = true;
    }

    public void setAuthorities(Collection<? extends UserRole> authorities) {
        this.authorities = authorities;
    }

}
