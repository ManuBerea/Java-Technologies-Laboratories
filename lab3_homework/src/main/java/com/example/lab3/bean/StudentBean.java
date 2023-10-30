package com.example.lab3.bean;

import com.example.lab3.database.DatabaseConnector;
import com.example.lab3.dto.ProjectDTO;
import com.example.lab3.dto.StudentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Getter
@Setter
@Named("studentBean")
public class StudentBean implements Serializable {
    private String name;
    private static int selectedStudentId = 1;

    private static final String INSERT_STUDENT_QUERY = "INSERT INTO students (name) VALUES (?);";
    private static final String SELECT_ALL_STUDENTS_QUERY = "SELECT * FROM students";
    private static final String DELETE_STUDENT_QUERY = "DELETE FROM students WHERE student_id = ?";
    private static final String SELECT_ASSOCIATED_PROJECTS_QUERY = "SELECT p.* FROM projects p JOIN student_projects ps ON p.project_id = ps.project_id WHERE ps.student_id = ?";
    private static final String SELECT_PROJECT_ID_QUERY = "SELECT project_id FROM projects WHERE name = ?";
    private static final String CHECK_ASSOCIATION_QUERY = "SELECT COUNT(*) FROM student_projects WHERE student_id = ? AND project_id = ?";
    private static final String INSERT_ASSOCIATION_QUERY = "INSERT INTO student_projects (project_id, student_id) VALUES (?, ?)";

    public void addStudent() {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_QUERY)) {

            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student added successfully!"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<StudentDTO> getAllStudents() {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<StudentDTO> students = new ArrayList<>();
            while (resultSet.next()) {
                StudentDTO dto = new StudentDTO(resultSet.getInt("student_id"), resultSet.getString("name"));
                students.add(dto);
            }
            return students;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteStudent(int studentId) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_QUERY)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showAssociatedProjects(int studentId) {
        selectedStudentId = studentId;
    }

    public List<ProjectDTO> getAssociatedProjects() {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ASSOCIATED_PROJECTS_QUERY)) {

            preparedStatement.setInt(1, selectedStudentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void addProjectToStudent(String projectName) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement selectProjectIdStatement = connection.prepareStatement(SELECT_PROJECT_ID_QUERY);
             PreparedStatement checkAssociationStatement = connection.prepareStatement(CHECK_ASSOCIATION_QUERY);
             PreparedStatement insertAssociationStatement = connection.prepareStatement(INSERT_ASSOCIATION_QUERY)) {

            selectProjectIdStatement.setString(1, projectName);
            try (ResultSet resultSet = selectProjectIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    int projectId = resultSet.getInt(1);

                    checkAssociationStatement.setInt(1, selectedStudentId);
                    checkAssociationStatement.setInt(2, projectId);
                    try (ResultSet associationResultSet = checkAssociationStatement.executeQuery()) {
                        if (associationResultSet.next() && associationResultSet.getInt(1) == 0) {
                            insertAssociationStatement.setInt(1, projectId);
                            insertAssociationStatement.setInt(2, selectedStudentId);
                            insertAssociationStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
