package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ruslan.dobrov.services.BooksService;
import ruslan.dobrov.services.PeopleService;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Value("${title.page.search.index}")
    private String indexPageTitle;

    @Value("${section.search}")
    private String sectionName;

    private final BooksService booksServices;
    private final PeopleService peopleService;

    @Autowired
    public SearchController(BooksService booksServices, PeopleService peopleService) {
        this.booksServices = booksServices;
        this.peopleService = peopleService;
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
        model.addAttribute("titlePage", indexPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "search/index";
    }
}
