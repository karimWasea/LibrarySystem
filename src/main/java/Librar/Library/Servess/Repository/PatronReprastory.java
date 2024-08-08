package Librar.Library.Servess.Repository;
import Librar.Library.Modes.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatronReprastory extends JpaRepository<Patron, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Patron b WHERE (:id IS NULL OR b.id != :id) AND b.name = :name  ")
    boolean existsByCriteria(@Param("id") Long id, @Param("name") String name);
}
