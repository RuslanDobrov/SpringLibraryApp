package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${title.page.main.index}")
    private String indexPageTitle;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("titlePage", indexPageTitle);
        return "/index";
    }
}