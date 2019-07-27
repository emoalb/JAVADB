package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesDto {
    @XmlElement(name = "category")
    private List<CategoryDto> categoryDtos;

    public CategoriesDto() {
    }

    public CategoriesDto(List<CategoryDto> categoryDtos) {
        this.categoryDtos = categoryDtos;
    }

    public List<CategoryDto> getCategoryDtos() {
        return categoryDtos;
    }

    public void setCategoryDtos(List<CategoryDto> categoryDtos) {
        this.categoryDtos = categoryDtos;
    }
}
