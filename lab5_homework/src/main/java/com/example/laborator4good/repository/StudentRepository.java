package com.example.laborator4good.repository;

import com.example.laborator4good.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class StudentRepository {

    @PersistenceContext(unitName = "UniversityManagementPU")
    private EntityManager entityManager;


    @Transactional
    public void add(Student student) {
        entityManager.persist(student);
    }

    public List<Student> findAllStudents() {
        return entityManager.createNamedQuery("Student.findAll", Student.class)
                .getResultList();
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.createNamedQuery("Student.deleteById")
                .setParameter("id", id)
                .executeUpdate();
    }

}
