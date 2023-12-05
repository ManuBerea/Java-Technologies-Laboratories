package com.example.laborator4good.controller;

import com.example.laborator4good.ejb.AssignmentTrackingBean;
import com.example.laborator4good.ejb.ProjectAssignmentBean;
import com.example.laborator4good.ejb.ProjectAvailabilityBean;
import com.example.laborator4good.model.Project;
import com.example.laborator4good.repository.ProjectRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Named
@RequestScoped
public class ProjectController {

    @EJB
    private ProjectRepository projectRepository;

    @EJB
    private ProjectAvailabilityBean projectAvailabilityBean;

    @EJB
    private ProjectAssignmentBean projectAssignmentBean;

    @EJB
    private AssignmentTrackingBean assignmentTrackingBean;

    private List<Long> selectedProjects;
    private Long selectedStudentId;

    private String name;
    private String category;
    private String description;
    private Date deadline;

    public Long getSelectedStudentId() {
        return selectedStudentId;
    }

    public void setSelectedStudentId(Long selectedStudentId) {
        this.selectedStudentId = selectedStudentId;
    }

    public List<Long> getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(List<Long> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public void assignSelectedProjectsToStudent() {
        if (selectedProjects != null) {
            projectAssignmentBean.setSelectedProjects(selectedProjects);

            projectAssignmentBean.assignSelectedProjectsToStudent(selectedStudentId);

            selectedProjects.forEach(projectId ->
                    assignmentTrackingBean.addAssignment(projectId, selectedStudentId));

            selectedProjects = null; // Resetează lista de proiecte selectate
        }
    }

    public void removeProjectFromStudent(Long projectId, Long studentId) {
        projectAssignmentBean.removeProjectFromStudent(projectId, studentId);
        assignmentTrackingBean.removeAssignment(projectId);
    }

    public void addProject() {
        Project project = new Project();
        project.setName(name);
        project.setCategory(category);
        project.setDescription(description);
        project.setDeadline(deadline);

        try {
            projectRepository.add(project);
        } catch (Exception e) {
            System.out.println("Error while adding project: " + e.getMessage());
        }
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAllProjects();
    }

    public List<Project> getAvailableProjects() {
        return projectRepository.findAllProjects().stream()
                .filter(p -> isProjectAvailable(p.getProject_id()))
                .collect(Collectors.toList());
    }

    public void deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error while deleting project: " + e.getMessage());
        }
    }

    public boolean isProjectAvailable(Long projectId) {
        return projectAvailabilityBean.isProjectAvailable(projectId);
    }

    public List<Project> getAllAssignedProjects() {
        return projectRepository.findAllWithAssignedStudents();
    }

}
