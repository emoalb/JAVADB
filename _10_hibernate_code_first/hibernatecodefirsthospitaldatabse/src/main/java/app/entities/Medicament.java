package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "medicaments")

public class Medicament extends BaseEntity {

    private String name;
    public Medicament() {
    }

    @Column(name="name",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length()==0){
            throw new IllegalArgumentException("Name can't be empty string");
        }
        this.name = name;
    }

}
