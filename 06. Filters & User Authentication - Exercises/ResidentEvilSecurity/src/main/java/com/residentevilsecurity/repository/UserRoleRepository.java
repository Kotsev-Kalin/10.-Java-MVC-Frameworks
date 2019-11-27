package com.residentevilsecurity.repository;

import com.residentevilsecurity.domain.entity.UserRole;
import com.residentevilsecurity.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(Role role);
}
