package com.example.laborator4good.repository;

import com.example.laborator4good.model.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProjectRepository {

    @PersistenceContext(unitName = "UniversityManagementPU")
    private EntityManager entityManager;

    public void add(Project project) {
        entityManager.persist(project);
    }

    public List<Project> findAllProjects() {
        return entityManager.createNamedQuery("Project.findAll", Project.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Project project = entityManager.find(Project.class, id);
        if (project != null) {
            entityManager.remove(project);
        }
    }

    public List<Project> findAllWithAssignedStudents() {
        return entityManager.createNamedQuery("Project.findAllWithAssignedStudents", Project.class).getResultList();
    }

}
