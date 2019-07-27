package com.softuni.xmlcardealer.repositories;

import com.softuni.xmlcardealer.domain.entities.Car;
import com.softuni.xmlcardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
}
