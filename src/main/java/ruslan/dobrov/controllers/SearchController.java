package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ruslan.dobrov.services.BooksService;


@Controller
@RequestMapping("/search")
public class SearchController {

    private final BooksService booksServices;

    @Autowired
    public SearchController(BooksService booksServices) {
        this.booksServices = booksServices;
    }

    @ModelAttribute("titlePage")
    public String titlePage() {
        return "Search book";
    }

    @ModelAttribute("currentPage")
    public String currentPage() {
        return "search";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "query", required = false) String query) {
        model.addAttribute("title", "Search book");
        model.addAttribute("currentPage", "search");
        if (query != null && !query.isEmpty()) {
            model.addAttribute("books", booksServices.findByTitleStartingWith(query));
        }
        return "search/index";
    }
}
