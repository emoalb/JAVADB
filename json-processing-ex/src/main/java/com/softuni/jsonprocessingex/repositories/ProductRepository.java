package com.softuni.jsonprocessingex.repositories;

import com.softuni.jsonprocessingex.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
