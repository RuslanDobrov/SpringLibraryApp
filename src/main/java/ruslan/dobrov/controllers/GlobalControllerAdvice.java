package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${default.page}")
    private int defaultPage;

    @Value("${default.books.per.page}")
    private int defaultBooksPerPage;

    @Value("${default.sort.by}")
    private String defaultSortBy;

    @Value("${sort.by.title}")
    private String sortByTitle;

    @Value("${sort.by.author}")
    private String sortByAuthor;

    @Value("${sort.by.yearPublished}")
    private String sortByYearPublished;

    @Value("${sort.by.totalQuantity}")
    private String sortByTotalQuantity;

    @ModelAttribute("defaultPage")
    public int defaultPage() {
        return defaultPage;
    }

    @ModelAttribute("defaultBooksPerPage")
    public int defaultBooksPerPage() {
        return defaultBooksPerPage;
    }

    @ModelAttribute("defaultSortBy")
    public String defaultSortBy() {
        return defaultSortBy;
    }

    @ModelAttribute("sortByTitle")
    public String sortByTitle() {
        return sortByTitle;
    }

    @ModelAttribute("sortByAuthor")
    public String sortByAuthor() {
        return sortByAuthor;
    }

    @ModelAttribute("sortByYearPublished")
    public String sortByYearPublished() {
        return sortByYearPublished;
    }

    @ModelAttribute("sortByTotalQuantity")
    public String sortByTotalQuantity() {
        return sortByTotalQuantity;
    }
}
