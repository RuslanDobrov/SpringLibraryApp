package ruslan.dobrov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruslan.dobrov.models.Book;
import ruslan.dobrov.models.Person;

import java.util.Date;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllById(int id);
    Person findByOwner(int person_id);
    List<Book> findByTitleStartingWith(String startingWith);
}
