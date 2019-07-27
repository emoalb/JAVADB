package com.softuni.xmlcardealer.util.implementation;

import com.softuni.xmlcardealer.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImplementation implements ValidatorUtil {
    private final Validator validator;

    @Autowired
    public ValidatorUtilImplementation(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).size()==0;
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return validator.validate(entity);
    }
}
