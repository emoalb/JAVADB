package com.softuni.xmlcardealer.domain.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {

    private String name;

    private Boolean isImporter;

    private Set<Part> parts;

    public Supplier() {

    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "importer")
    public Boolean getIsImporter() {
        return this.isImporter;
    }

    public void setIsImporter(Boolean isImporter) {
        this.isImporter = isImporter;
    }

    @OneToMany(targetEntity = Part.class, mappedBy = "supplier", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
