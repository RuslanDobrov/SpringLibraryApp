package ruslan.dobrov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.models.PersonBook;
import ruslan.dobrov.repositories.PeopleRepository;
import ruslan.dobrov.repositories.PersonBookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    @PersistenceContext
    private EntityManager entityManager;

    private final PeopleRepository peopleRepository;
    private final PersonBookRepository personBookRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PersonBookRepository personBookRepository) {
        this.peopleRepository = peopleRepository;
        this.personBookRepository = personBookRepository;
    }

    public List<Person> findAll() {
        List<Person> people = peopleRepository.findAll();
        Hibernate.initialize(people);
        return people;
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Person> findAllPeopleWithoutThisBook(int book_id) {
        List<PersonBook> peopleWithThisBook = personBookRepository.findAllByBookId(book_id);
        List<Person> people = peopleRepository.findAll();
        for (PersonBook person : peopleWithThisBook) {
            people.remove(person.getPerson());
        }
        Hibernate.initialize(people);
        return people;
    }

    public List<PersonBook> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<PersonBook> books = personBookRepository.findAllByPersonId(id);
            for (PersonBook book : books) {
                book.setExpired(isExpired(book));
            }
            return books;
        } else {
            return Collections.emptyList();
        }
    }

    public List<Person> searchPersonByName(String keyword) {
        List<Person> people = entityManager.createQuery("SELECT p FROM Person p WHERE LOWER(p.fullName) LIKE LOWER(:keyword)", Person.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
        for (Person person : people) {
            Hibernate.initialize(person.getBooks());
        }
        return people;
    }

    public Boolean isExpired(PersonBook book) {
        Date today = new Date();
        return ChronoUnit.DAYS.between(book.getAssignDate().toInstant(), today.toInstant()) > 10;
    }

    public Person getPersonByFullName(String fullName) {
        return peopleRepository.findPersonByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
