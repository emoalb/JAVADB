package com.softuni.xmlcardealer.repositories;

import com.softuni.xmlcardealer.domain.entities.Sale;
import com.softuni.xmlcardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Integer> {
}
