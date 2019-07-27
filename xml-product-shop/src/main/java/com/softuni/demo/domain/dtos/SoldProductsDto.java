package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDto {
    @XmlElement(name = "product")
    private List<ProductDto> soldProducts;

    public SoldProductsDto() {
    }

    public SoldProductsDto(List<ProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public List<ProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
