package com.softuni.jsonprocessingex.domain.dtos;

import com.google.gson.annotations.Expose;
import com.softuni.jsonprocessingex.domain.entities.Category;
import com.softuni.jsonprocessingex.domain.entities.User;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class ProductSeedDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    @Expose
    private Set<Category> categories;
    @Expose
    private User buyer;
    @Expose
    private User seller;


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
