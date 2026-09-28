package com.eventease.service;

import com.eventease.entity.Student;
import com.eventease.exception.ResourceNotFoundException;
import com.eventease.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Service class for Student - contains all business logic related to students
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students from the database
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a single student by ID
    // Throws ResourceNotFoundException if student does not exist
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + id));
    }

    // Create a new student and save to database
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // Update an existing student's information
    public Student updateStudent(Long id, Student updatedStudent) {
        // First check if the student exists
        Student existingStudent = getStudentById(id);

        // Update the fields
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setYearOfStudy(updatedStudent.getYearOfStudy());

        // Save and return the updated student
        return studentRepository.save(existingStudent);
    }

    // Delete a student from the database
    public void deleteStudent(Long id) {
        // Check if student exists before deleting
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
