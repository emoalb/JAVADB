package com.softuni.demo.domain.dtos;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesSeedDto {
    @XmlElement(name = "category")
    private List<CategorySeedDto> categories;

    public CategoriesSeedDto(List<CategorySeedDto> categories) {
        this.categories = categories;
    }

    public CategoriesSeedDto() {
    }

    public List<CategorySeedDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySeedDto> categories) {
        this.categories = categories;
    }
}
