package com.productshop.service;

import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;

public interface UserRoleService {
    UserRole findByRole(Role role);
}
