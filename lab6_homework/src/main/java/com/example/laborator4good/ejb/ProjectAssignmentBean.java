package com.example.laborator4good.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.example.laborator4good.model.Project;
import com.example.laborator4good.model.Student;

import java.util.ArrayList;
import java.util.List;

@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProjectAssignmentBean {

    @PersistenceContext(unitName = "UniversityManagementPU")
    private EntityManager entityManager;

    private List<Long> selectedProjects;

    @PostConstruct
    public void init() {
        selectedProjects = new ArrayList<>();
    }

    public void setSelectedProjects(List<Long> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    @Remove
    public void assignSelectedProjectsToStudent(Long studentId) {
        for (Long projectId : selectedProjects) {
            Project project = entityManager.find(Project.class, projectId);
            Student student = entityManager.find(Student.class, studentId);
            if (project != null && student != null) {
                project.setStudent(student);
                entityManager.merge(project);
            }
        }
    }

    @Remove
    public void removeProjectFromStudent(Long projectId, Long studentId) {
        Project project = entityManager.find(Project.class, projectId);
        Student student = entityManager.find(Student.class, studentId);
        if (project != null && student != null) {
            project.removeStudent();
            entityManager.merge(project);
        }
    }

}
