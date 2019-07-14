package com.softuni.jsonprocessingex.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.jsonprocessingex.util.FileIO;
import com.softuni.jsonprocessingex.util.implementation.FileIOImplementation;

import com.softuni.jsonprocessingex.util.ValidatorUtil;
import com.softuni.jsonprocessingex.util.implementation.ValidatorUtilImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting().create();
    }

    @Bean
    public FileIO fileUtil() {
        return new FileIOImplementation();
    }

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
