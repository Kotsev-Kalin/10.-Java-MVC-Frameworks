package com.residentevilsecurity.service;

import com.residentevilsecurity.domain.entity.User;
import com.residentevilsecurity.domain.entity.UserRole;
import com.residentevilsecurity.domain.enums.Role;
import com.residentevilsecurity.domain.model.service.UserRoleServiceModel;
import com.residentevilsecurity.domain.model.service.UserServiceModel;
import com.residentevilsecurity.repository.UserRepository;
import com.residentevilsecurity.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel save(UserServiceModel userServiceModel) {
        try {
            User user = this.modelMapper.map(userServiceModel, User.class);
            user.setAuthorities(userServiceModel.getAuthorities()
                    .stream()
                    .map(userRoleServiceModel -> this.modelMapper.map(userRoleServiceModel, UserRole.class))
                    .collect(Collectors.toSet()));
            User saved = this.userRepository.save(user);
            return this.modelMapper.map(saved, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.modelMapper.map(this.userRepository.findByUsername(username), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(encoder.encode(userServiceModel.getPassword()));
        userServiceModel.setAuthorities(this.generateUserRole());
        UserServiceModel saved = this.save(userServiceModel);
        return saved != null;
    }

    @Override
    public void switchRole(String id, UserRoleServiceModel userRoleServiceModel) {
        UserServiceModel model = this.modelMapper.map(this.userRepository.findById(id).get(), UserServiceModel.class);
        if (model.getAuthorities().stream().anyMatch(x -> x.getRole().equals(Role.ROLE_ROOT))) {
            return;
        }
        model.setAuthorities(new HashSet<UserRoleServiceModel>() {{
            add(userRoleServiceModel);
        }});
        this.update(model);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    @Override
    public UserServiceModel update(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setAuthorities(userServiceModel.getAuthorities().stream()
                .map(a -> this.userRoleRepository.findByRole(a.getRole()).get())
                .collect(Collectors.toCollection(HashSet::new)));
        try {
            User saved = this.userRepository.save(user);
            return this.modelMapper.map(saved, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Set<UserRoleServiceModel> generateUserRole() {
        Set<UserRoleServiceModel> roles = new HashSet<>();
        if (this.userRepository.count() == 0) {
            roles.add(this.modelMapper
                    .map(this.userRoleRepository.findByRole(Role.ROLE_ROOT).get(), UserRoleServiceModel.class));
        } else {
            roles.add(this.modelMapper
                    .map(this.userRoleRepository.findByRole(Role.ROLE_USER).get(), UserRoleServiceModel.class));
        }

        return roles;
    }
}
