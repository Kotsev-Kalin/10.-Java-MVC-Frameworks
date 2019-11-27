package softuni.exodia.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exodia.domain.model.binding.UserLoginBindingModel;
import softuni.exodia.domain.model.binding.UserRegisterBindingModel;
import softuni.exodia.domain.model.service.UserServiceModel;
import softuni.exodia.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView confirmRegister(@ModelAttribute(name = "model") UserRegisterBindingModel model, ModelAndView modelAndView) {
        if (!model.getConfirmPassword().equals(model.getPassword()) ||
                !this.userService.register(this.modelMapper.map(model, UserServiceModel.class))) {
            modelAndView.setViewName("redirect:/register");
        } else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView confirmLogin(@ModelAttribute(name = "model") UserLoginBindingModel model,
                                     ModelAndView modelAndView, HttpSession session) {
        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        if (!this.userService.login(userServiceModel)) {
            modelAndView.setViewName("redirect:/login");
        } else {
            session.setAttribute("userId", userServiceModel.getId());
            session.setAttribute("username", userServiceModel.getUsername());
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
