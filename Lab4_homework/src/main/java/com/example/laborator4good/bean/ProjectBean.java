package com.example.laborator4good.bean;

import com.example.laborator4good.model.Project;
import lombok.Data;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
@Data
public class ProjectBean implements Serializable {

    private List<Project> projects;
    @Resource(name = "jdbc/L4Pool")
    private DataSource dataSource;
    private Long project_id;
    private String name;
    private String category;
    private String description;
    private Date deadline;

    public List<Project> getAllProjects() {

        List<Project> projects = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            String selectQuery = "SELECT * FROM projects";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Project project = new Project();
                    project.setProject_id(resultSet.getLong("project_id"));
                    project.setName(resultSet.getString("name"));
                    project.setCategory(resultSet.getString("category"));
                    project.setDescription(resultSet.getString("description"));
                    project.setDeadline(resultSet.getDate("deadline"));

                    projects.add(project);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public void submit() {

        try (Connection connection = dataSource.getConnection()) {

            String insertQuery = "INSERT INTO projects (name, category, description, deadline) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.category);
                preparedStatement.setString(3, this.description);
                preparedStatement.setDate(4, new java.sql.Date(this.deadline.getTime()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public void deleteProject(Long projectId) {

        try (Connection connection = dataSource.getConnection()) {

            String deleteQuery = "DELETE FROM projects WHERE project_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setLong(1, projectId);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
