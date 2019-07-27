package com.softuni.demo.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersSeedDto {
    @XmlElement(name = "user")
    private List<UserSeedDto> userSeedDtos;

    public UsersSeedDto(List<UserSeedDto> userSeedDtos) {
        this.userSeedDtos = userSeedDtos;
    }

    public UsersSeedDto() {
    }

    public List<UserSeedDto> getUserSeedDtos() {
        return userSeedDtos;
    }

    public void setUserSeedDtos(List<UserSeedDto> userSeedDtos) {
        this.userSeedDtos = userSeedDtos;
    }
}
