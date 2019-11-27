package softuni.exodia.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exodia.domain.model.binding.DocumentCreateBindingModel;
import softuni.exodia.domain.model.service.DocumentServiceModel;
import softuni.exodia.domain.model.view.DocumentDetailsViewModel;
import softuni.exodia.service.DocumentService;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView confirmSchedule(@ModelAttribute(name = "model") DocumentCreateBindingModel model, ModelAndView modelAndView) {
        DocumentServiceModel documentServiceModel = this.documentService.register(this.modelMapper.map(model, DocumentServiceModel.class));
        if (documentServiceModel != null) {
            modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        } else {
            modelAndView.setViewName("redirect:/schedule");
        }
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            DocumentDetailsViewModel documentDetailsViewModel = parseDocument(id);
            modelAndView.setViewName("details");
            modelAndView.addObject("document", documentDetailsViewModel);
        }
        return modelAndView;
    }

    @GetMapping("/print/{id}")
    public ModelAndView delete(@PathVariable(name = "id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            DocumentDetailsViewModel documentDetailsViewModel = parseDocument(id);
            modelAndView.setViewName("print");
            modelAndView.addObject("document", documentDetailsViewModel);
        }
        return modelAndView;
    }

    private DocumentDetailsViewModel parseDocument(@PathVariable(name = "id") String id) {
        DocumentServiceModel documentServiceModel = this.documentService.findById(id);
        if (documentServiceModel == null) {
            throw new IllegalArgumentException("Document not found");
        }
        return this.modelMapper.map(documentServiceModel, DocumentDetailsViewModel.class);
    }

    @PostMapping("/print/{id}")
    public ModelAndView confirmDelete(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        this.documentService.deleteById(id);
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
