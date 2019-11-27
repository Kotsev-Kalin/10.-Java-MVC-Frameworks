package com.productshop.service;

import com.productshop.domain.enums.Role;
import com.productshop.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel save(UserServiceModel userServiceModel);

    List<UserServiceModel> findAll();

    UserServiceModel findByUsername(String username);

    UserServiceModel findById(String id);

    UserServiceModel switchRole(String id, Role userRole);

    UserServiceModel update(UserServiceModel map);
}
