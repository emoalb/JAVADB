package com.softuni.demo.repositories;

import com.softuni.demo.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal startingPrice, BigDecimal endingPrice);
}
