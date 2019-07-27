package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSeedDto {

    @XmlElement(name = "product")
    private List<ProductSeedDto> productSeedDtos;

    public ProductsSeedDto(List<ProductSeedDto> productSeedDtos) {
        this.productSeedDtos = productSeedDtos;
    }

    public ProductsSeedDto() {
    }

    public List<ProductSeedDto> getProductSeedDtos() {
        return productSeedDtos;
    }

    public void setProductSeedDtos(List<ProductSeedDto> productSeedDtos) {
        this.productSeedDtos = productSeedDtos;
    }
}
