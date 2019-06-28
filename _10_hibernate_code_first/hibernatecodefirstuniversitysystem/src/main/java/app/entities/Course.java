package app.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
private String name;
private String description;
private Timestamp startDate;
private Timestamp endDate;
private Set<Student> studentSet;
private Teacher teacher;
    public Course() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
@ManyToMany
@JoinTable(name="cources_studnets",
joinColumns = @JoinColumn(name = "column_id",referencedColumnName = "id"),
inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }
@ManyToOne
@JoinColumn(name = "techer_id",referencedColumnName = "id")
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
