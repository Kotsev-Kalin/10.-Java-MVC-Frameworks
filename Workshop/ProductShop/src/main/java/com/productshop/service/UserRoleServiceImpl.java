package com.productshop.service;

import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;
import com.productshop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole findByRole(Role role) {
        return this.userRoleRepository.findByRole(role);
    }
}
