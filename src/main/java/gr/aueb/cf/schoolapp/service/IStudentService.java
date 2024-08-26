package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;

import java.util.List;

public interface IStudentService {
    Student insertStudent(StudentInsertDTO dto) throws StudentDAOException;
    Student updateStudent(StudentUpdateDTO dto) throws StudentNotFoundException, StudentDAOException;
    void deleteStudent(Integer id) throws StudentDAOException, StudentNotFoundException;
    Student getStudentById(Integer id) throws StudentNotFoundException, StudentDAOException;
    List<Student> getStudentsByLastname(String lastname) throws StudentDAOException;
}
