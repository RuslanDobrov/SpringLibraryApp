package ruslan.dobrov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Person> findAllWithPaginationAndSortByColumn(int page, int recPerPage, String columnName) {
        PageRequest pageRequest = PageRequest.of(page, recPerPage, Sort.by(columnName));
        return peopleRepository.findAll(pageRequest);
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
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
        List<Person> people = entityManager
                .createQuery("SELECT p FROM Person p WHERE LOWER(p.fullName) LIKE LOWER(:keyword)", Person.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
        for (Person person : people) {
            Hibernate.initialize(person.getBooks());
        }
        return people;
    }

    public List<Person> searchPersonByNameWithoutBook(String keyword, int book_id) {
        String sqlQuery = "SELECT p.* " +
                          "FROM Person p " +
                          "WHERE lower(p.full_name) LIKE :keyword " +
                          "AND NOT EXISTS (" +
                          "    SELECT 1 " +
                          "    FROM Person_Book pb " +
                          "    WHERE pb.person_id = p.person_id " +
                          "    AND pb.book_id = :bookId)";

        List<Person> people = entityManager.createNativeQuery(sqlQuery, Person.class)
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .setParameter("bookId", book_id)
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