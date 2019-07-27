package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findCategoryByName(String name);
    @Query(value = "SELECT *,COUNT(i.category_id)AS coun,SUM(i.price) as prs  from categories as c JOIN items i on c.id = i.category_id GROUP BY  i.category_id ORDER BY coun desc , prs desc ;",nativeQuery = true)

    List<Category> findAllOrderByItemsCountAndPrice();
}
