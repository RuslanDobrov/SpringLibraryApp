package ruslan.dobrov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping("/functionality")
    public String functionalityPage() {
        return "about/functionality";
    }

    @GetMapping("/technologies")
    public String technologiesPage() {
        return "about/technologies";
    }

}
