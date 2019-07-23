package alararestaurant.domain.dtos;

import alararestaurant.domain.entities.Position;

public class EmployeeSeedDto {
    private Integer age;
    private String name;
    private Position position;

    public EmployeeSeedDto() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
