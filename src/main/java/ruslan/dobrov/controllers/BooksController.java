package ruslan.dobrov.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.services.BooksService;
import ruslan.dobrov.services.PeopleService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Value("${title.page.books.edit}")
    private String editPageTitle;

    @Value("${title.page.books.index}")
    private String indexPageTitle;

    @Value("${title.page.books.new}")
    private String newPageTitle;

    @Value("${title.page.books.show}")
    private String showPageTitle;

    @Value("${section.books}")
    private String sectionName;

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksServices, PeopleService peopleService) {
        this.booksService = booksServices;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "recPerPage", defaultValue = "${records.per.page}") int recPerPage,
                        @RequestParam(value = "sortBy",  defaultValue = "${sort.books.by.title}") String columnName) {
        int totalBooks = booksService.findAll().size();
        int totalPages = (int) Math.ceil((double) totalBooks / recPerPage);

        // pagination
        page = Math.max(0, Math.min(page, totalPages - 1));

        Page<Book> sortedBooks = booksService.findAllWithPaginationAndSortByColumn(page, recPerPage, columnName);
        model.addAttribute("books", sortedBooks);
        model.addAttribute("page", page);
        model.addAttribute("records", recPerPage);

        // list of buttons for pages
        List<Integer> pageNumbers = IntStream.range(0, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("numberOfPages", pageNumbers);
        model.addAttribute("titlePage", indexPageTitle);
        model.addAttribute("sectionName", sectionName);

        return "books/index";
    }

    @GetMapping("/{book_id}")
    @Transactional
    public String show(Model model,
                       @PathVariable("book_id") int book_id,
                       @ModelAttribute("person") Person person,
                       @RequestParam(value = "query", required = false) String keyword) {
        Book book = booksService.findOne(book_id);
        Hibernate.initialize(book.getOwners());
        model.addAttribute("book", book);
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("people", peopleService.searchPersonByNameWithoutBook(keyword, book_id));
        }
        model.addAttribute("owners", book.getOwners());
        model.addAttribute("titlePage", showPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("titlePage", newPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("titlePage", editPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release/{person_id}")
    public String release(@PathVariable("person_id") int person_id, @PathVariable("book_id") int book_id) {
        booksService.release(person_id, book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id") int book_id, @ModelAttribute("person_id") int person_id) {
        booksService.assign(person_id, book_id);
        return "redirect:/books/" + book_id;
    }
}