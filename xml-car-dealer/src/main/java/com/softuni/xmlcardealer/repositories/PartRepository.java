package com.softuni.xmlcardealer.repositories;

import com.softuni.xmlcardealer.domain.entities.Part;
import com.softuni.xmlcardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part,Integer> {
}
