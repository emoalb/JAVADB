package com.softuni.demo.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="categories")
public class Category extends  BaseEntity {
    private String name;
    private Set<Product> products;
    public Category(){

    }
@Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ManyToMany(targetEntity = Product.class, mappedBy = "categories",fetch = FetchType.EAGER,cascade = CascadeType.ALL
    )
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
