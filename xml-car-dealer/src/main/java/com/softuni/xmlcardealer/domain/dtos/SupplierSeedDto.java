package com.softuni.xmlcardealer.domain.dtos;


import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedDto {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "is-importer")
    private String isImporter;

    public SupplierSeedDto() {
    }

    @NotNull(message = "name of supplier cannot be null")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsImporter() {
        return isImporter;
    }

    public void setIsImporter(String isImporter) {
        this.isImporter = isImporter;
    }
}
