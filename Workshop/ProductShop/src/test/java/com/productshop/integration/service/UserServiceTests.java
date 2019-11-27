package com.productshop.integration.service;


import com.productshop.domain.entity.User;
import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;
import com.productshop.domain.model.service.UserServiceModel;
import com.productshop.repository.UserRepository;
import com.productshop.repository.UserRoleRepository;
import com.productshop.service.UserRoleService;
import com.productshop.service.UserRoleServiceImpl;
import com.productshop.service.UserService;
import com.productshop.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    private UserRoleService userRoleService;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder encoder;
    private UserService userService;
    private List<User> users;

    @Before
    public void setup() {
        this.modelMapper = new ModelMapper();
        this.encoder = new BCryptPasswordEncoder();
        this.userRoleService = new UserRoleServiceImpl(userRoleRepository);
        users = new ArrayList<>();
        this.userService = new UserServiceImpl(userRepository, userRoleService, encoder, modelMapper);
        UserRole role = new UserRole();
        role.setRole(Role.ROLE_USER);
        this.userRoleRepository.save(role);
        UserRole roleRoot = new UserRole();
        roleRoot.setRole(Role.ROLE_ROOT);
        this.userRoleRepository.save(roleRoot);
    }

    @Test
    public void save_withCorrectParameters_forwardsEntityToDatabase() {

    }

    @Test
    public void findAll_whenNoUsers_returnsEmptyList() {
        List<UserServiceModel> result = userService.findAll();
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void findAll_when2Users_returnsListWithSize2() {
        addSingleUser("gosho", "gosho@abv.bg");
        addSingleUser("maria", "maria@abv.bg");
        List<UserServiceModel> result = userService.findAll();
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void findById_whenCorrectId_returnsCorrectUser() {
        String id = this.userRepository.findAll().get(0).getId();
        UserServiceModel result = userService.findById(id);
        Assert.assertEquals("pesho@abv.bg", result.getEmail());
        Assert.assertEquals("pesho", result.getUsername());
    }

    @Test
    public void findById_withNonexistentId_returnsNull() {
        UserServiceModel result = userService.findById("random_id");
        Assert.assertNull(result);
    }

    @Test
    public void create_withCorrectParameters_returnsModel() {
        UserServiceModel model = new UserServiceModel();
        model.setEmail("ivan@abv.bg");
        model.setUsername("ivan");
        model.setPassword("123");
        UserServiceModel saved = this.userService.register(model);
        Assert.assertEquals(saved.getEmail(), model.getEmail());
        Assert.assertEquals(saved.getUsername(), model.getUsername());
        Assert.assertNotEquals("123", saved.getPassword());
    }

    @Test
    public void create_withEmptyDatabase_userIsRoot(){
        this.userRepository.deleteAll();
        UserServiceModel model = new UserServiceModel();
        model.setEmail("ivan@abv.bg");
        model.setUsername("ivan");
        model.setPassword("123");
        UserServiceModel saved = this.userService.register(model);
        Assert.assertEquals(Role.ROLE_ROOT, saved.getAuthorities().get(0).getRole());
    }

    @Test
    public void create_withNonEmptyDatabase_userIsUser(){
        addSingleUser("pesho", "pesho@abv.bg");
        UserServiceModel model = new UserServiceModel();
        model.setEmail("ivan@abv.bg");
        model.setUsername("ivan");
        model.setPassword("123");
        UserServiceModel saved = this.userService.register(model);
        Assert.assertEquals(Role.ROLE_USER, saved.getAuthorities().get(0).getRole());
    }

    private void addSingleUser(String username, String email) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword("123");
        List<UserRole> authorities = new ArrayList<>();
        authorities.add(this.userRoleRepository.findByRole(Role.ROLE_USER));
        user.setAuthorities(authorities);
        this.userRepository.save(user);
    }
}
