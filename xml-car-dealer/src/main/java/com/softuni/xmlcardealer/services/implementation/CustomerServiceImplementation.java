package com.softuni.xmlcardealer.services.implementation;

import com.softuni.xmlcardealer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImplementation implements CustomerService {
    @Autowired
    public CustomerServiceImplementation() {
    }
}
