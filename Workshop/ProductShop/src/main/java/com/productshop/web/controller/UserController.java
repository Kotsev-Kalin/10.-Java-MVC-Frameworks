package com.productshop.web.controller;

import com.productshop.domain.entity.User;
import com.productshop.domain.entity.UserRole;
import com.productshop.domain.enums.Role;
import com.productshop.domain.model.binding.UserEditBindingModel;
import com.productshop.domain.model.binding.UserRegisterBindingModel;
import com.productshop.domain.model.service.UserServiceModel;
import com.productshop.domain.model.view.UserViewModel;
import com.productshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
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
                this.userService.register(this.modelMapper.map(model, UserServiceModel.class)) != null) {
            modelAndView.setViewName("redirect:/users/login");
        } else {
            modelAndView.setViewName("redirect:/users/register");
        }
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView all(ModelAndView modelAndView, Authentication authentication) {
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.setViewName("all-users");
        modelAndView.addObject("users", this.mapUserViewModels(id));
        modelAndView.addObject("rootrole", Role.ROLE_ROOT);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("moderatorrole", Role.ROLE_MODERATOR);
        modelAndView.addObject("userrole", Role.ROLE_USER);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, Authentication authentication) {
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.addObject("user", this.modelMapper.map(this.userService.findById(id), UserViewModel.class));
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView edit(ModelAndView modelAndView, Authentication authentication) {
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.addObject("user", this.modelMapper.map(this.userService.findById(id), UserViewModel.class));
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView confirmEdit(@ModelAttribute(name = "model") UserEditBindingModel model,
                                    ModelAndView modelAndView, Authentication authentication) {
        String id = ((User) authentication.getPrincipal()).getId();
        if (model.getOldPassword() == null || !model.getPassword().equals(model.getConfirmPassword())) {
            modelAndView.setViewName("redirect:/users/edit");
        } else {
            UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
            userServiceModel.setId(id);
            this.userService.update(userServiceModel);
            modelAndView.setViewName("redirect:/users/profile");
        }
        return modelAndView;
    }

    @GetMapping("/switchrole/admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_ADMIN);
    }

    @GetMapping("/switchrole/user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_USER);
    }

    @GetMapping("/switchrole/moderator/{id}")
    public ModelAndView makeModerator(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_MODERATOR);
    }

    private ModelAndView changeRoleOfUser(ModelAndView modelAndView, String id, Role role) {
        this.userService.switchRole(id, role);
        modelAndView.setViewName("redirect:/users/all");
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
                            .map(UserRole::getRole)
                            .collect(Collectors.toList());
                    model.setAuthorities(roles);
                    model.setRolesAsStrings(roles.stream().map(x -> x.name().substring(5)).collect(Collectors.toList()));
                    return model;
                })
                .collect(Collectors.toList());
    }
}
