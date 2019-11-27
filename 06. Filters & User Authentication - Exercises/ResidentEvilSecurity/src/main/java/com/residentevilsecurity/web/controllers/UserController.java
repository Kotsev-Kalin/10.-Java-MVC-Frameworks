package com.residentevilsecurity.web.controllers;

import com.residentevilsecurity.domain.entity.User;
import com.residentevilsecurity.domain.enums.Role;
import com.residentevilsecurity.domain.model.binding.UserRegisterBindingModel;
import com.residentevilsecurity.domain.model.service.UserRoleServiceModel;
import com.residentevilsecurity.domain.model.service.UserServiceModel;
import com.residentevilsecurity.domain.model.view.UserViewModel;
import com.residentevilsecurity.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView confirmRegister(@ModelAttribute(name = "model") UserRegisterBindingModel model,
                                        ModelAndView modelAndView) {
        if (model.getPassword().equals(model.getConfirmPassword()) &&
                this.userService.register(this.modelMapper.map(model, UserServiceModel.class))) {
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView users(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("users");
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.addObject("users", mapUserViewModels(id));
        modelAndView.addObject("rootrole", Role.ROLE_ROOT);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("moderatorrole", Role.ROLE_MODERATOR);
        modelAndView.addObject("userrole", Role.ROLE_USER);
        return modelAndView;
    }

    @GetMapping("/users/switchrole/admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_ADMIN);
    }

    @GetMapping("/users/switchrole/user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_USER);
    }

    @GetMapping("/users/switchrole/moderator/{id}")
    public ModelAndView makeModerator(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_MODERATOR);
    }

    private ModelAndView changeRoleOfUser(ModelAndView modelAndView, String id, Role role) {
        this.userService.switchRole(id, new UserRoleServiceModel() {{
            setRole(role);
        }});
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    private List<UserViewModel> mapUserViewModels(String id) {
        List<UserServiceModel> allUsers = this.userService.findAll();
        return allUsers.stream()
                .filter(user -> user.getAuthorities().stream().noneMatch(a -> a.getRole().equals(Role.ROLE_ROOT))
                        && !user.getId().equals(id))
                .map(user -> {
                    UserViewModel model = this.modelMapper.map(user, UserViewModel.class);
                    List<Role> roles = user.getAuthorities()
                            .stream()
                            .map(UserRoleServiceModel::getRole)
                            .collect(Collectors.toList());
                    model.setAuthorities(roles);
                    return model;
                })
                .collect(Collectors.toList());
    }

}
