package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.services.BooksServices;
import ruslan.dobrov.services.PeopleService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksServices booksServices;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksServices booksServices, PeopleService peopleService) {
        this.booksServices = booksServices;
        this.peopleService = peopleService;
    }

//    @GetMapping()
//    public String index(Model model) {
//        model.addAttribute("books", booksServices.findAll());
//        return "books/index";
//    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "-1", required = false) int page,
                        @RequestParam(value = "books_per_page", defaultValue = "-1", required = false) int booksPerPage,
                        @RequestParam(value = "sort_by", defaultValue = "", required = false) String columName) {
        if (page != -1 && booksPerPage != -1) {
            model.addAttribute("books", booksServices.findAllWithPagination(page, booksPerPage));
            model.addAttribute("page", page);
            model.addAttribute("booksPerPage", booksPerPage);
            if(booksServices.findAll().size() > 10) {
                model.addAttribute("numberOfPages",
                        IntStream.range(
                                page > 5 ? (page - 5) : 0,
                                (booksServices.findAll().size() - page) > 5 ? (page + 5) : booksServices.findAll().size()
                        ).boxed().collect(Collectors.toList()));
            }
            else {
                model.addAttribute("numberOfPages", IntStream.range(0, booksServices.findAll().size() / booksPerPage).boxed().collect(Collectors.toList()));
            }
        } else if (!columName.isEmpty()){
           model.addAttribute("books", booksServices.findAllWithSortByColum(columName));
        } else {
            model.addAttribute("books", booksServices.findAll());
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksServices.findOne(id));
        Person bookOwner = booksServices.getBookOwner(id);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.findAll());
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
        booksServices.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksServices.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        booksServices.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksServices.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksServices.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksServices.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
