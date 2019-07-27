package com.softuni.xmlcardealer.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="sales")
public class Sale extends BaseEntity {

    private BigDecimal discount;
    private Customer customer;
    private Car car;

    public Sale() {

    }

    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToOne(targetEntity = Car.class )
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
