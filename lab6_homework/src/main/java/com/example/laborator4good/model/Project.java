package com.example.laborator4good.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
        @NamedQuery(name = "Project.findAllWithAssignedStudents", query = "SELECT p FROM Project p WHERE p.student IS NOT NULL")
})

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Student getAssignedStudent() {
        return student;
    }

    public void assignStudent(Student student) {
        this.student = student;
    }

    public void removeStudent() {
        this.student = null;
    }
}
