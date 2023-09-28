package ruslan.dobrov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruslan.dobrov.models.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
//    Optional<Book> findBookByBookId(int book_id);
    List<Book> findBookByTitleStartingWith(String string);
}
