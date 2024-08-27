package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOImplTest {

    private StudentDAOImpl studentDAO;

    @BeforeEach
    void setUp() throws Exception {
        studentDAO = new StudentDAOImpl();

        try (Connection connection = DBUtil.getConnection();
             Statement stmt = connection.createStatement()) {
            // Clean up the table before each test
            String sql = "DELETE FROM students";
            stmt.executeUpdate(sql);

            // Reset the auto-increment counter
            sql = "ALTER TABLE students AUTO_INCREMENT = 1";
            stmt.executeUpdate(sql);
        }
    }

    @AfterEach
    void tearDown() {
        // Any necessary cleanup after each test can be done here.
    }

    @Test
    void insert() throws Exception {
        Student student = new Student(null, "John", "Doe");
        Student insertedStudent = studentDAO.insert(student);

        assertNotNull(insertedStudent);
        assertEquals("John", insertedStudent.getFirstname());
        assertEquals("Doe", insertedStudent.getLastname());
    }

    @Test
    void update() throws Exception {
        Student student = new Student(null, "John", "Doe");
        studentDAO.insert(student);

        student.setFirstname("Jane");
        Student updatedStudent = studentDAO.update(student);

        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstname());
    }

    @Test
    void delete() throws Exception {
        Student student = new Student(null, "John", "Doe");
        Student insertedStudent = studentDAO.insert(student);

        studentDAO.delete(insertedStudent.getId());

        Student retrievedStudent = studentDAO.getById(insertedStudent.getId());
        assertNull(retrievedStudent);
    }

    @Test
    void getById() throws Exception {
        Student student = new Student(null, "John", "Doe");
        Student insertedStudent = studentDAO.insert(student);

        Student retrievedStudent = studentDAO.getById(insertedStudent.getId());

        assertNotNull(retrievedStudent);
        assertEquals("John", retrievedStudent.getFirstname());
        assertEquals("Doe", retrievedStudent.getLastname());
    }

    @Test
    void getByLastname() throws Exception {
        Student student1 = new Student(null, "John", "Doe");
        studentDAO.insert(student1);

        Student student2 = new Student(null, "Jane", "Doe");
        studentDAO.insert(student2);

        List<Student> students = studentDAO.getByLastname("Doe");

        assertNotNull(students);
        assertEquals(2, students.size());
    }
}
