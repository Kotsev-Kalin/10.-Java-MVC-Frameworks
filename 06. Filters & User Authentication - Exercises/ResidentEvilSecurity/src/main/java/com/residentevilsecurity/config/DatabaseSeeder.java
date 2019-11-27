package com.residentevilsecurity.config;


import com.residentevilsecurity.domain.entity.UserRole;
import com.residentevilsecurity.domain.enums.Role;
import com.residentevilsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseSeeder {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DatabaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    public void seed() {
        if (this.userRoleRepository.findAll().isEmpty()) {
            UserRole userRole = new UserRole();
            userRole.setRole(Role.ROLE_USER);
            UserRole moderatorRole = new UserRole();
            moderatorRole.setRole(Role.ROLE_MODERATOR);
            UserRole adminRole = new UserRole();
            adminRole.setRole(Role.ROLE_ADMIN);
            UserRole rootRole = new UserRole();
            rootRole.setRole(Role.ROLE_ROOT);
            this.userRoleRepository.saveAndFlush(userRole);
            this.userRoleRepository.saveAndFlush(adminRole);
            this.userRoleRepository.saveAndFlush(moderatorRole);
            this.userRoleRepository.saveAndFlush(rootRole);
        }
    }
}
