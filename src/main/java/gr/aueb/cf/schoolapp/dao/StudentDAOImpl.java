package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {

    @Override
    public Student insert(Student student) throws StudentDAOException {
        String sql = "INSERT INTO students (firstname, lastname) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // extract model info
            String firstname = student.getFirstname();
            String lastname = student.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);

            ps.executeUpdate();
            // logging
            return student; // TBD
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in insert student: " + student);
        }
    }

    @Override
    public Student update(Student student) throws StudentDAOException {
        String sql = "UPDATE students SET firstname = ?, lastname = ? WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // extract model info
            int id = student.getId();
            String firstname = student.getFirstname();
            String lastname = student.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, id);

            ps.executeUpdate();

//            if (n == 0) {
//                return null;
//            }
            // logging
            return student; // TBD
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in update student: " + student);
        }
    }

    @Override
    public void delete(Integer id) throws StudentDAOException {
        String sql = "DELETE FROM STUDENTS WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            // logging
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in delete student with student id: " + id);
        }
    }

    @Override
    public Student getById(Integer id) throws StudentDAOException {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in get by id with id: " + id);
        }
    }

    @Override
    public List<Student> getByLastname(String lastname) throws StudentDAOException {
        List<Student> students = new ArrayList<>(); // isEmpty == true
        ResultSet rs;
        String sql = "SELECT * FROM students WHERE lastname LIKE ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();
            // logging

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new StudentDAOException("SQL error in get by lastname with lastname: " + lastname);
        }

    }
}