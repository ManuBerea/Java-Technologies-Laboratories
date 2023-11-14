package com.example.laborator4good.controller;

import com.example.laborator4good.model.Student;
import com.example.laborator4good.repository.StudentRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@Data
@Named
@RequestScoped
public class StudentController {

    @EJB
    private StudentRepository studentRepository;

    private String name;

    public void addStudent() {
        Student student = new Student();
        student.setName(name);
        try {
            studentRepository.add(student);
        } catch (Exception e) {
            System.out.println("Error while adding student: " + e.getMessage());
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllStudents();
    }

    public void deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error while deleting student: " + e.getMessage());
        }
    }
}
