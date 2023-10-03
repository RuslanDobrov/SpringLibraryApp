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
import ruslan.dobrov.services.PersonBookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private final PersonBookService personBookService;

    @Autowired
    public BooksController(BooksService booksServices, PeopleService peopleService, PersonBookService personBookService) {
        this.booksService = booksServices;
        this.peopleService = peopleService;
        this.personBookService = personBookService;
    }

    @ModelAttribute("titlePage")
    public String titlePage() {
        return "Books";
    }

    @ModelAttribute("currentPage")
    public String currentPage() {
        return "books";
    }

    @ModelAttribute("booksPerPage")
    public String booksPerPage() {
        return "${books.per.page}";
    }

    @ModelAttribute("sortByTitle")
    public String sortByTitle() {
        return "${sort.by.title}";
    }

    @ModelAttribute("sortByAuthor")
    public String sortByAuthor() {
        return "${sort.by.author}";
    }

    @ModelAttribute("sortByYearPublished")
    public String sortByYearPublished() {
        return "${sort.by.yearPublished}";
    }

    @ModelAttribute("sortByTotalQuantity")
    public String sortByTotalQuantity() {
        return "${sort.by.totalQuantity}";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page", defaultValue = "${books.per.page}") int booksPerPage,
                        @RequestParam(value = "sort_by",  defaultValue = "${sort.by.title}") String columnName) {
        int totalBooks = booksService.findAll().size();
        int totalPages = (int) Math.ceil((double) totalBooks / booksPerPage);

        // Ограничьте номер страницы в диапазоне от 0 до (totalPages - 1).
        page = Math.max(0, Math.min(page, totalPages - 1));

        Page<Book> sortedBooks = booksService.findAllWithPaginationAndSortByColumn(page, booksPerPage, columnName);
        model.addAttribute("books", sortedBooks);
        model.addAttribute("page", page);
        model.addAttribute("booksPerPage", booksPerPage);

        // Создайте список кнопок для страниц.
        List<Integer> pageNumbers = IntStream.range(0, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("numberOfPages", pageNumbers);

        return "books/index";
    }

    @GetMapping("/{book_id}")
    @Transactional
    public String show(@PathVariable("book_id") int book_id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(book_id);
        Hibernate.initialize(book.getOwners());
        model.addAttribute("book", book);
        List<Person> bookOwner = book.getOwners();
        model.addAttribute("people", peopleService.findAllPeopleWithoutThisBook(book_id));
        model.addAttribute("owners", bookOwner);
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
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
    public String assign(@PathVariable("book_id") int book_id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(selectedPerson.getId(), book_id);
        return "redirect:/books/" + book_id;
    }
}

/*
@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;
    private final PersonBookService personBookService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService, PersonBookService personBookService) {
        this.bookService = bookService;
        this.personService = personService;
        this.personBookService = personBookService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "-1", required = false) int page,
                        @RequestParam(value = "books_per_page", defaultValue = "-1", required = false) int booksPerPage,
                        @RequestParam(value = "sort_by", defaultValue = "", required = false) String columnName) {
        if (page != -1 && booksPerPage != -1) {
            model.addAttribute("books", bookService.findAllWithPagination(page, booksPerPage));
            model.addAttribute("page", page);
            model.addAttribute("booksPerPage", booksPerPage);
            if (bookService.findAll().size() > 10) {
                model.addAttribute("numberOfPages",
                        IntStream.range(
                                page > 5 ? (page - 5) : 0,
                                (bookService.findAll().size() - page) > 5 ? (page + 5) : bookService.findAll().size()
                        ).boxed().collect(Collectors.toList()));
            } else {
                model.addAttribute("numberOfPages", IntStream.range(0, bookService.findAll().size() / booksPerPage).boxed().collect(Collectors.toList()));
            }
        } else if (!columnName.isEmpty()) {
            model.addAttribute("books", bookService.findAllWithSortByColumn(columnName));
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        Person bookOwner = book.getOwner();
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            List<Person> people = personService.findAll();
            model.addAttribute("people", people);
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam("personId") int personId) {
        Person selectedPerson = personService.findOne(personId);
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}*/
