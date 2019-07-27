package mostwanted.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "racers")
public class Racer extends BaseEntity {
    private Integer age;
    private String name;
    private BigDecimal bounty;
    private Town homeTown;
    private Set<Car> cars;
    private Set<RaceEntry> raceEntries;

    public Racer() {
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "name",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "bounty",precision = 19,scale = 2)
    public BigDecimal getBounty() {
        return bounty;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }


    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    @OneToMany(targetEntity = Car.class, mappedBy = "racer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }


    @OneToMany(targetEntity = RaceEntry.class,mappedBy = "racer",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<RaceEntry> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(Set<RaceEntry> raceEntries) {
        this.raceEntries = raceEntries;
    }
}
