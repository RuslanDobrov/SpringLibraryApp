package ruslan.dobrov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruslan.dobrov.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
