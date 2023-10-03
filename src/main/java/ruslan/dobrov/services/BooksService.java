package ruslan.dobrov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.models.PersonBook;
import ruslan.dobrov.repositories.BooksRepository;
import ruslan.dobrov.repositories.PeopleRepository;
import ruslan.dobrov.repositories.PersonBookRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    private final PersonBookRepository personBookRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository,
                        PeopleRepository peopleRepository,
                        PersonBookRepository personBookRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
        this.personBookRepository = personBookRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllWithSortByColumn(String columnName) {
        return booksRepository.findAll(Sort.by(columnName));
    }

    public Page<Book> findAllWithPaginationAndSortByColumn(int page, int booksPerPage, String columnName) {
        PageRequest pageRequest = PageRequest.of(page, booksPerPage, Sort.by(columnName));
        return booksRepository.findAll(pageRequest);
    }

    public Book findOne(int book_id) {
        Optional<Book> foundBook = booksRepository.findById(book_id);
        return foundBook.orElse(null);
    }

    public List<Book> findByTitleStartingWith(String title) {
        List<Book> books = booksRepository.findBookByTitleStartingWith(title); // Получение объекта Book
        for (Book book : books) {
            Hibernate.initialize(book.getOwners()); // Инициализация коллекции owners для каждой книги
        }
       return books;
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int book_id, Book updateBook) {
        updateBook.setId(book_id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int book_id) {
        booksRepository.deleteById(book_id);
    }

    public List<Person> getBookOwners(int book_id) {
        Optional<Book> foundBook = booksRepository.findById(book_id);
        return foundBook.orElse(null).getOwners();
    }

    @Transactional
    public void release(int person_id, int book_id) {
        PersonBook personBook = personBookRepository.findByPersonIdAndBookId(person_id, book_id);
        Book book = personBook.getBook();
        book.setTotalQuantity(book.getTotalQuantity() + 1);
        personBookRepository.delete(personBook);
        booksRepository.save(book);
    }

    @Transactional
    public void assign(int person_id, int book_id) {
        Person person = peopleRepository.findById(person_id).get();
        Book book = booksRepository.findById(book_id).get();
        PersonBook personBook = new PersonBook(person, book);
        personBook.setAssignDate(new Date());
        book.setTotalQuantity(book.getTotalQuantity() - 1);
        personBookRepository.save(personBook);
    }
}
