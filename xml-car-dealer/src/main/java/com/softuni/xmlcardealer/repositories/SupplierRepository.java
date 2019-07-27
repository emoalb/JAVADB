package com.softuni.xmlcardealer.repositories;

import com.softuni.xmlcardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
}
