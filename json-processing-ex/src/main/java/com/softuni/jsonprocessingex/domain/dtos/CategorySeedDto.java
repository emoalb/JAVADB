package com.softuni.jsonprocessingex.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CategorySeedDto implements Serializable {
@Expose
    private String name;

    public CategorySeedDto() {

    }

    public CategorySeedDto(String name) {
        this.name = name;
    }

    @Size(min=3 ,max=15,message="Invalid name(between 3 and 15 chars)")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
