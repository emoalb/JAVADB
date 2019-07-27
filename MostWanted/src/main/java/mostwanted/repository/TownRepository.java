package mostwanted.repository;


import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {
Optional<Town> findByName(String name);

@Query(value = "SELECT t FROM Town t JOIN t.racers r WHERE r IS NOT NULL GROUP BY t.name ORDER  BY size(r) desc ,t.name asc")
List<Town> sortedTowns();

}
