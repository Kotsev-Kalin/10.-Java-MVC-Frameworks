package com.productshop.service;

import com.productshop.domain.entity.User;
import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;
import com.productshop.domain.model.service.UserServiceModel;
import com.productshop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, BCryptPasswordEncoder encoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        userServiceModel.setAuthorities(new ArrayList<UserRole>() {{
            add(setInitialRole());
        }});
        return this.save(userServiceModel);
    }

    @Override
    public UserServiceModel save(UserServiceModel userServiceModel) {
        try {
            User user = this.modelMapper.map(userServiceModel, User.class);
            this.userRepository.save(user);
            return this.modelMapper.map(user, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }

    private UserRole setInitialRole() {
        return this.userRoleService
                .findByRole(this.userRepository.count() == 0 ? Role.ROLE_ROOT : Role.ROLE_USER);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public UserServiceModel switchRole(String id, Role userRole) {
        UserServiceModel userServiceModel = this.findById(id);
        if (userServiceModel != null &&
                userServiceModel.getAuthorities().stream().noneMatch(role -> role.getRole().equals(Role.ROLE_ROOT))) {
            userServiceModel.setAuthorities(new ArrayList<UserRole>() {{
                add(userRoleService.findByRole(userRole));
            }});
            this.userRepository.save(this.modelMapper.map(userServiceModel, User.class));
            return userServiceModel;
        }
        return null;
    }

    @Override
    public UserServiceModel update(UserServiceModel userServiceModel) {
        UserServiceModel oldModel = this.findById(userServiceModel.getId());
        if (encoder.matches(oldModel.getPassword(), userServiceModel.getPassword())) return null;
        if (userServiceModel.getPassword() != null) {
            userServiceModel.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        } else {
            userServiceModel.setPassword(oldModel.getPassword());
        }
        userServiceModel.setAuthorities(oldModel.getAuthorities());
        User savedUser = this.userRepository.save(this.modelMapper.map(userServiceModel, User.class));
        return this.modelMapper.map(savedUser, UserServiceModel.class);
    }
}
