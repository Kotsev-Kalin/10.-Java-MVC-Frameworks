package com.residentevilsecurity.service;

import com.residentevilsecurity.domain.model.service.UserRoleServiceModel;
import com.residentevilsecurity.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    UserServiceModel save(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    List<UserServiceModel> findAll();

    boolean register(UserServiceModel map);

    void switchRole(String id, UserRoleServiceModel userRole);

    UserServiceModel update(UserServiceModel userServiceModel);
}
