package com.productshop.repository;

import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByRole(Role role);
}
