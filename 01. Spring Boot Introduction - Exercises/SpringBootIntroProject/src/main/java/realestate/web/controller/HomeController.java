package realestate.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.model.view.OfferViewModel;
import realestate.service.OfferService;
import realestate.util.HtmlReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final OfferService offerService;
    private final HtmlReader htmlReader;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(OfferService offerService, HtmlReader htmlReader, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.htmlReader = htmlReader;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ResponseBody
    public String index() throws IOException {
        return this.formatHtml();
    }

    private String formatHtml() throws IOException {
        List<OfferViewModel> offerViewModels = this.offerService.findAll()
                .stream()
                .map(offerServiceModel -> this.modelMapper.map(offerServiceModel, OfferViewModel.class))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        if (offerViewModels.size() == 0) {
            sb.append("<div class=\"apartment\" style=\"border: 1px solid red\">");
            sb.append("There aren't any offers!");
            sb.append("</div>");
        } else {
            offerViewModels.forEach(offerViewModel -> {
                sb.append("<div class=\"apartment\">");
                sb.append("    <p>Rent: ").append(offerViewModel.getApartmentRent()).append("</p>");
                sb.append("    <p>Type: ").append(offerViewModel.getApartmentType()).append("</p>");
                sb.append("    <p>Commission: ").append(offerViewModel.getAgencyCommission()).append("</p>");
                sb.append("</div>");
            });
        }
        return this.htmlReader.readHtmlFile("D:\\Програмиране\\СофтУни\\Java Web\\SoftUni_Java_Web\\Java MVC Frameworks\\1.2 Spring Boot Introduction - Exercises\\SpringBootIntroProject\\src\\main\\resources\\static\\index.html")
                .replace("{{offers}}", sb.toString());
    }
}
