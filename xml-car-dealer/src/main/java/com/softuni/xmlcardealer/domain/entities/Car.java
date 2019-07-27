package com.softuni.xmlcardealer.domain.entities;



import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;

    private String model;

    private String traveledDistance;

    private Sale sale;

    private Set<Part> parts;

    public Car() {
    }

    @Column(name = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "travelled_distance")
    public String getTraveledDistance() {
        return traveledDistance;
    }

    public void setTraveledDistance(String traveledDistance) {
        this.traveledDistance = traveledDistance;
    }
    @OneToOne(targetEntity = Sale.class,mappedBy = "car",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
    @ManyToMany(targetEntity = Part.class, mappedBy = "cars",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
