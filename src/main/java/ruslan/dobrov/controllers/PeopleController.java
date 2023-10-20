package ruslan.dobrov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.services.PeopleService;
import ruslan.dobrov.util.PersonValidator;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Value("${title.page.people.edit}")
    private String editPageTitle;

    @Value("${title.page.people.index}")
    private String indexPageTitle;

    @Value("${title.page.people.new}")
    private String newPageTitle;

    @Value("${title.page.people.show}")
    private String showPageTitle;

    @Value("${section.people}")
    private String sectionName;

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "recPerPage", defaultValue = "${records.per.page}") Integer recPerPage,
                        @RequestParam(value = "sortBy",  defaultValue = "${sort.people.by.fullName}") String columnName) {
        int totalPeople = peopleService.findAll().size();
        int totalPages = (int) Math.ceil((double) totalPeople / recPerPage);

        // pagination
        page = Math.max(0, Math.min(page, totalPages - 1));

        Page<Person> sortedPeople = peopleService.findAllWithPaginationAndSortByColumn(page, recPerPage, columnName);
        model.addAttribute("people", sortedPeople);
        model.addAttribute("page", page);
        model.addAttribute("records", recPerPage);

        // list of buttons for pages
        List<Integer> pageNumbers = IntStream.range(0, totalPages)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("numberOfPages", pageNumbers);
        model.addAttribute("titlePage", indexPageTitle);
        model.addAttribute("sectionName", sectionName);

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        model.addAttribute("titlePage", showPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("titlePage", newPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("titlePage", editPageTitle);
        model.addAttribute("sectionName", sectionName);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}