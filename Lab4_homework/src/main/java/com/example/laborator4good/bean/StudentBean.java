package com.example.laborator4good.bean;

import com.example.laborator4good.model.Student;
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
import java.util.List;

@ManagedBean
@ViewScoped
@Data
public class StudentBean implements Serializable {

    private List<Student> students;
    @Resource(name = "jdbc/L4Pool")
    private DataSource dataSource;
    private String name;

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM students";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Student student = new Student();
                    student.setStudent_id(resultSet.getInt("student_id"));
                    student.setName(resultSet.getString("name"));

                    students.add(student);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public void submit() {

        try (Connection connection = dataSource.getConnection()) {

            String insertQuery = "INSERT INTO students (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, this.name);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(Long studentId) {

        try (Connection connection = dataSource.getConnection()) {

            String deleteQuery = "DELETE FROM students WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setLong(1, studentId);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
