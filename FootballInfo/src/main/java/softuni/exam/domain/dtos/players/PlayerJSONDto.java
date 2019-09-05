package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.enums.Position;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class PlayerJSONDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer number;
    @Expose
    private BigDecimal salary;
    @Expose
    private Position position;
    @Expose
    private PictureJSONDto picture;
    @Expose
    private TeamJSONDto team;


    public PlayerJSONDto() {
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Size(min = 3, max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Min(value = 1)
    @Max(value = 99)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @NotNull
    @DecimalMin(value = "0")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PictureJSONDto getPicture() {
        return picture;
    }

    public void setPicture(PictureJSONDto picture) {
        this.picture = picture;
    }

    public TeamJSONDto getTeam() {
        return team;
    }

    public void setTeam(TeamJSONDto team) {
        this.team = team;
    }
}
