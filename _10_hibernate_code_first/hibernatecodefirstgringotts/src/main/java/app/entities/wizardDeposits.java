package app.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "wizard_deposits")

public class wizardDeposits {
    private Integer id;
    private String firstName;
    private String lastName;
    private String notes;
    private Integer age;
    private String magicWandCreator;
    private Integer magicWandSize;
    private String depositGroup;
    private Timestamp depositStartDate;
    private Double depositAmount;
    private Double depositInterest;
    private Double depositCharge;
    private Boolean isDepositExpired;

    public wizardDeposits() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "notes", length = 1000)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size")
    public Integer getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(Integer magicWandSize) {
        if (magicWandSize <= 0) {
            throw new IllegalArgumentException("Magic wand size must be bigger than 0");
        }
        this.magicWandSize = magicWandSize;
    }
    @Column(name="deposit_group",length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }

    @Column(name = "deposit_start_date")
    public Timestamp getDepositStartDate() {
        return depositStartDate;

    }
    public void setDepositStartDate(Timestamp depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    @Column(name = "deposit_amount")
    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Column(name = "deposit_interest")
    public Double getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(Double depositInterest) {
        this.depositInterest = depositInterest;
    }
    @Column(name = "deposit_charge")
    public Double getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(Double depositCharge) {
        this.depositCharge = depositCharge;
    }
    @Column(name = "is_deposit_expired")
    public Boolean getDepositExpired() {
        return isDepositExpired;
    }

    public void setDepositExpired(Boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}
