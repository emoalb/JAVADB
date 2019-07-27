package com.softuni.demo.configuration;


import com.softuni.demo.util.ValidatorUtil;
import com.softuni.demo.util.implementation.ValidatorUtilImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImplementation(validator());
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
