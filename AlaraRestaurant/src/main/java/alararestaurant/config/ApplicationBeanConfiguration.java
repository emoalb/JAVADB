package alararestaurant.config;

import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.implementation.FileUtilImplementation;
import alararestaurant.util.implementation.ValidationUtilImplementation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {

        return new FileUtilImplementation();
    }

    @Bean
    public Gson gson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidationUtil validationUtil() {

        return new ValidationUtilImplementation(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
