package com.softuni.jsonprocessingex.repositories;


import com.softuni.jsonprocessingex.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
