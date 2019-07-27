package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductDto {
    @XmlElement(name = "user")
    private List<UserWithSoldProductsDto> userWithSoldProductsDtos;

    public UsersWithSoldProductDto() {
    }

    public UsersWithSoldProductDto(List<UserWithSoldProductsDto> userWithSoldProductsDtos) {
        this.userWithSoldProductsDtos = userWithSoldProductsDtos;
    }

    public List<UserWithSoldProductsDto> getUserWithSoldProductsDtos() {
        return userWithSoldProductsDtos;
    }

    public void setUserWithSoldProductsDtos(List<UserWithSoldProductsDto> userWithSoldProductsDtos) {
        this.userWithSoldProductsDtos = userWithSoldProductsDtos;
    }
}
