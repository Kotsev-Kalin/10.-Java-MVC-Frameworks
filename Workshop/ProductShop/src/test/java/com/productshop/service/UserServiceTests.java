package com.productshop.service;

import com.productshop.repository.UserRepository;
import com.productshop.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private UserRoleService userRoleService;
    private BCryptPasswordEncoder encoder;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.encoder = new BCryptPasswordEncoder();
        this.userRoleService = new UserRoleServiceImpl(userRoleRepository);
        this.userService = new UserServiceImpl(this.userRepository, userRoleService, encoder, this.modelMapper);
    }

    @Test
    public void save_withCorrectParameters_forwardsEntityToDatabase(){

    }
}
