package com.example.lab3.bean;

import com.example.lab3.database.DatabaseConnector;
import com.example.lab3.dto.ProjectDTO;
import com.example.lab3.dto.StudentDTO;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@Getter
@Setter
@Named("projectBean")
public class ProjectBean implements Serializable {
    private String projectName;
    private String projectCategory;
    private String projectDescription;
    private Date projectDeadline;
    private DualListModel<StudentDTO> selectedStudents;

    private static final String SELECT_PROJECTS_QUERY = "SELECT * FROM projects";
    private static final String INSERT_PROJECT_QUERY = "INSERT INTO projects (name, category, description, deadline) VALUES (?, ?, ?, ?)";
    private static final String DELETE_PROJECT_QUERY = "DELETE FROM projects WHERE project_id = ?";
    private static final String DELETE_STUDENT_PROJECT_QUERY = "DELETE FROM student_projects WHERE project_id = ?";

    public List<ProjectDTO> getAllProjects() {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECTS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<ProjectDTO> projects = new ArrayList<>();
            while (resultSet.next()) {
                ProjectDTO project = new ProjectDTO(
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline")
                );
                projects.add(project);
            }
            return projects;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addProject() {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_QUERY)) {

            preparedStatement.setString(1, projectName);
            preparedStatement.setString(2, projectCategory);
            preparedStatement.setString(3, projectDescription);
            preparedStatement.setDate(4, new java.sql.Date(projectDeadline.getTime()));
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(int projectId) {
        try (Connection connection = DatabaseConnector.connect()) {
            if (connection != null) {
                try (PreparedStatement deleteProjectStatement = connection.prepareStatement(DELETE_PROJECT_QUERY);
                     PreparedStatement deleteProjectStudentStatement = connection.prepareStatement(DELETE_STUDENT_PROJECT_QUERY)) {

                    connection.setAutoCommit(false);

                    deleteProjectStudentStatement.setInt(1, projectId);
                    deleteProjectStudentStatement.executeUpdate();

                    deleteProjectStatement.setInt(1, projectId);
                    deleteProjectStatement.executeUpdate();

                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
