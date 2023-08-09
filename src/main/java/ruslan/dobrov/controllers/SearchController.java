package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ruslan.dobrov.services.BooksServices;

import java.util.ArrayList;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final BooksServices booksServices;

    @Autowired
    public SearchController(BooksServices booksServices) {
        this.booksServices = booksServices;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("books", booksServices.findByTitleStartingWith(query));
        }
        return "search/index";
    }
}
