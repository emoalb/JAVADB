package com.softuni.xmlcardealer.repositories;

import com.softuni.xmlcardealer.domain.entities.Customer;
import com.softuni.xmlcardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
