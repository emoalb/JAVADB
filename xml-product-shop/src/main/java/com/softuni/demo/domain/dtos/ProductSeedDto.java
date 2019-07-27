package com.softuni.demo.domain.dtos;

import com.softuni.demo.domain.entities.Category;
import com.softuni.demo.domain.entities.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDto {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlTransient
    private Set<Category> categories;
    @XmlTransient
    private User buyer;
    @XmlTransient
    private User seller;

    public ProductSeedDto() {
    }

    public ProductSeedDto(String name, BigDecimal price, User byer, User seller, Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.buyer = byer;
        this.seller = seller;
        this.categories = categories;
    }

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name should be at least 3 chars")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @NotNull(message = "Seller cannot be null")
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @NotNull(message = "Add categories")
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
