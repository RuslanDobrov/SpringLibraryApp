package ruslan.dobrov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @ModelAttribute("titlePage")
    public String titlePage() {
        return "Spring Library App";
    }

    @GetMapping()
    public String index() {
        return "/index";
    }
}