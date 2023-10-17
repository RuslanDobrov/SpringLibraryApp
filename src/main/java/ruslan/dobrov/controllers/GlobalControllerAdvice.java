package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${default.page.number}")
    private int defaultPageNumber;

    @Value("${default.records.per.page}")
    private int defaultRecordsPerPage;

    @Value("${default.books.sort.by}")
    private String defaultBooksSortBy;

    @Value("${default.people.sort.by}")
    private String defaultPeopleSortBy;

    @Value("${sort.books.by.title}")
    private String sortBooksByTitle;

    @Value("${sort.books.by.author}")
    private String sortBooksByAuthor;

    @Value("${sort.books.by.yearPublished}")
    private String sortBooksByYearPublished;

    @Value("${sort.books.by.totalQuantity}")
    private String sortBooksByTotalQuantity;

    @Value("${sort.people.by.fullName}")
    private String sortPeopleByFullName;

    @Value("${sort.people.by.birthYear}")
    private String sortPeopleByBirthYear;

    @ModelAttribute("defaultPageNumber")
    public int defaultPageNumber() {
        return defaultPageNumber;
    }

    @ModelAttribute("defaultRecordsPerPage")
    public int defaultRecordsPerPage() {
        return defaultRecordsPerPage;
    }

    @ModelAttribute("defaultBooksSortBy")
    public String defaultBooksSortBy() {
        return defaultBooksSortBy;
    }

    @ModelAttribute("defaultPeopleSortBy")
    public String defaultPeopleSortBy() {
        return defaultPeopleSortBy;
    }

    @ModelAttribute("sortBooksByTitle")
    public String sortBooksByTitle() {
        return sortBooksByTitle;
    }

    @ModelAttribute("sortBooksByAuthor")
    public String sortBooksByAuthor() {
        return sortBooksByAuthor;
    }

    @ModelAttribute("sortBooksByYearPublished")
    public String sortBooksByYearPublished() {
        return sortBooksByYearPublished;
    }

    @ModelAttribute("sortBooksByTotalQuantity")
    public String sortBooksByTotalQuantity() {
        return sortBooksByTotalQuantity;
    }

    @ModelAttribute("sortPeopleByFullName")
    public String sortPeopleByFullName() {
        return sortPeopleByFullName;
    }

    @ModelAttribute("sortPeopleByBirthYear")
    public String sortPeopleByBirthYear() {
        return sortPeopleByBirthYear;
    }
}
