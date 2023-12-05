package com.example.laborator4good.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.example.laborator4good.model.Project;

@Stateless
public class ProjectAvailabilityBean {
    @PersistenceContext(unitName = "UniversityManagementPU")
    private EntityManager entityManager;

    public boolean isProjectAvailable(Long projectId) {
        if (projectId == null) {
            return false;
        }
        Project project = entityManager.find(Project.class, projectId);
        return project != null && project.getAssignedStudent() == null;
    }

}
