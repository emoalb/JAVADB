package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsDto {
    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-product")
    private SoldProductsDto soldProductsDto;

    public UserWithSoldProductsDto() {
    }

    public UserWithSoldProductsDto(String firstName, String lastName, SoldProductsDto soldProductsDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProductsDto = soldProductsDto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SoldProductsDto getSoldProductsDto() {
        return soldProductsDto;
    }

    public void setSoldProductsDto(SoldProductsDto soldProductsDto) {
        this.soldProductsDto = soldProductsDto;
    }
}
