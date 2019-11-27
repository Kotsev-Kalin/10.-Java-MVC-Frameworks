package softuni.exodia.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.exodia.domain.model.view.DocumentListViewModel;
import softuni.exodia.service.DocumentService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView homeLoggedIn(ModelAndView modelAndView, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            List<DocumentListViewModel> documents = this.documentService
                    .findAll()
                    .stream()
                    .map(d -> this.modelMapper.map(d, DocumentListViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.setViewName("home");
            modelAndView.addObject("documents", documents);
        }
        return modelAndView;
    }
}
