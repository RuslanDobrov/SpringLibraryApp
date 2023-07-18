package ruslan.dobrov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT id, title, author, year FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Person showBookWithPerson(int id) {
        return jdbcTemplate.query("SELECT Person.name FROM Book JOIN Person on Person.id = Book.person_id WHERE Book.id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id > 0", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ?, person_id = ? WHERE id = ?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getPerson_id(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public void takeOff(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = -1 WHERE id = ?", id);
    }
}
