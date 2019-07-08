package com.softuni.springadvancedqueringlab.repository;


import com.softuni.springadvancedqueringlab.domain.entities.Shampoo;
import com.softuni.springadvancedqueringlab.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySize(Size size);
}
