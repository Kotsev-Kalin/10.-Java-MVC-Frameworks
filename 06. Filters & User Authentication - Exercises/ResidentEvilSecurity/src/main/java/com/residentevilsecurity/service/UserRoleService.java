package com.residentevilsecurity.service;

import com.residentevilsecurity.domain.enums.Role;
import com.residentevilsecurity.domain.model.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    UserRoleServiceModel save(UserRoleServiceModel userRoleServiceModel);

    List<UserRoleServiceModel> findAll();

    UserRoleServiceModel findByRole(Role role);
}
