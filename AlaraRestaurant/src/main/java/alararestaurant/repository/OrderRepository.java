package alararestaurant.repository;

import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.Position;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByEmployee_PositionOrderByEmployeeId(Position position);
    List<Order> findAllByEmployee_Position_NameOrderByEmployee_Name(String position);

}
