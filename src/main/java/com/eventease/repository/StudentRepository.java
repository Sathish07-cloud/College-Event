package com.eventease.repository;

import com.eventease.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository for Student - provides database operations for Student entity
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find a student by their email address
    Optional<Student> findByEmail(String email);
}
