package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ruslan.dobrov.services.BooksService;
import ruslan.dobrov.services.PeopleService;


@Controller
@RequestMapping("/search")
public class SearchController {

    private final BooksService booksServices;
    private final PeopleService peopleService;

    @Autowired
    public SearchController(BooksService booksServices, PeopleService peopleService) {
        this.booksServices = booksServices;
        this.peopleService = peopleService;
    }

    @ModelAttribute("titlePage")
    public String titlePage() {
        return "Search";
    }

    @ModelAttribute("currentPage")
    public String currentPage() {
        return "search";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "query", required = false) String query,
                        @RequestParam(value = "search-option", required = false) String searchOption) {
        if (query != null && !query.isEmpty()) {
            if (searchOption.equals("title")) {
                model.addAttribute("books", booksServices.searchBooksByTitle(query));
            }
            if (searchOption.equals("author")) {
                model.addAttribute("books", booksServices.searchBooksByAuthor(query));
            }
            if (searchOption.equals("person")) {
                model.addAttribute("people", peopleService.searchPersonByName(query));
            }
        }
        return "search/index";
    }
}
