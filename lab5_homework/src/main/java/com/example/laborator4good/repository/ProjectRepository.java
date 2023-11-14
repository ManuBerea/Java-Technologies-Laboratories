package com.example.laborator4good.repository;

import com.example.laborator4good.model.Project;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ProjectRepository {
    @PersistenceContext(unitName = "UniversityManagementPU")
    private EntityManager entityManager;

    @Transactional
    public void add(Project project) {
        entityManager.persist(project);
    }

    public List<Project> findAllProjects() {
        return entityManager.createNamedQuery("Project.findAll", Project.class)
                .getResultList();
    }
    @Transactional
    public void deleteById(Long id) {
        entityManager.createNamedQuery("Project.deleteById")
                .setParameter("id", id)
                .executeUpdate();
    }

}
