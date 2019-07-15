package com.softuni.jsonprocessingex.domain.entities;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<Product> buyingProduct;
    private Set<Product> sellingProduct;
    private Set<User> friends;

    public User() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Product> getBuyingProduct() {
        return buyingProduct;
    }

    public void setBuyingProduct(Set<Product> buyingProduct) {
        this.buyingProduct = buyingProduct;
    }

    @OneToMany(targetEntity = Product.class, mappedBy = "seller",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Product> getSellingProduct() {
        return sellingProduct;
    }


    public void setSellingProduct(Set<Product> sellingProduct) {
        this.sellingProduct = sellingProduct;
    }


    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
