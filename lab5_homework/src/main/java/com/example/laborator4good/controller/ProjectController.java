package com.example.laborator4good.controller;

import com.example.laborator4good.model.Project;
import com.example.laborator4good.repository.ProjectRepository;
import lombok.Data;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;

@Data
@Named
@RequestScoped
public class ProjectController {

    @EJB
    private ProjectRepository projectRepository;

    private String name;
    private String category;
    private String description;
    private Date deadline;

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

    public void deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error while deleting project: " + e.getMessage());
        }
    }
}
