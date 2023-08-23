package ruslan.dobrov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;
import ruslan.dobrov.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksServices {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksServices(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findAllWithSortByColum(String columName) {
        return booksRepository.findAll(Sort.by(columName));
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
       return booksRepository.findByTitleStartingWith(startingWith);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null).getOwner();
    }

    @Transactional
    public void release(int id) {
        Book foundBook = booksRepository.findById(id).get();
        foundBook.setOwner(null);
        foundBook.setDateAssign(null);
        booksRepository.save(foundBook);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book foundBook = booksRepository.findById(id).get();
        foundBook.setOwner(selectedPerson);
        foundBook.setDateAssign(new Date());
        booksRepository.save(foundBook);
    }
}
