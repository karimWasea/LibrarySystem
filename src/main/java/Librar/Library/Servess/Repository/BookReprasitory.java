package Librar.Library.Servess.Repository;
 import Librar.Library.Modes.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookReprasitory extends JpaRepository<Book, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE (:id IS NULL OR b.Id != :id) AND b.title = :title AND b.isbn = :isbn")
    boolean existsByCriteria(@Param("id") Long id, @Param("title") String title, @Param("isbn") String isbn);
}
