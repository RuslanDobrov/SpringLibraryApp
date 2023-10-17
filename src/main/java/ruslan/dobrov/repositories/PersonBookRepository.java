package ruslan.dobrov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ruslan.dobrov.models.PersonBook;

import java.util.List;

public interface PersonBookRepository extends JpaRepository<PersonBook, Integer> {
    PersonBook findByPersonIdAndBookId(int personId, int bookId);
    List<PersonBook> findAllByPersonId(int personId);
    List<PersonBook> findAllByBookId(int bookId);
}
