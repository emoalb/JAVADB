package mostwanted.repository;
import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer,Integer> {
Optional<Racer> findByName(String name);

@Query("SELECT r FROM Racer r JOIN r.cars c WHERE c IS NOT NULL GROUP BY r.name ORDER BY size(c) desc,r.name asc")
List<Racer> findRacersWithCars();
}
