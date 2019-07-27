package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name="category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryDto {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "products-count")
    private Integer productsCount;
    @XmlElement(name = "average-price")
    private BigDecimal averagePrice;
    @XmlElement(name="total-revenue")
    private BigDecimal totalRevenue;

    public CategoryDto() {
    }

    public CategoryDto(String name, Integer productsCount, BigDecimal averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
