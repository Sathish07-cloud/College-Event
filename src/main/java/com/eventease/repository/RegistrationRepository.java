package com.eventease.repository;

import com.eventease.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// Repository for Registration - provides database operations for Registration entity
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Find all registrations for a specific event with a specific status
    // Used to count REGISTERED seats for an event
    List<Registration> findByEventIdAndStatus(Long eventId, String status);

    // Find all registrations made by a specific student
    List<Registration> findByStudentId(Long studentId);

    // Find all registrations for a specific event
    List<Registration> findByEventId(Long eventId);

    // Check if a student already has a registration for an event with a specific status
    // Used to prevent duplicate REGISTERED registrations
    Optional<Registration> findByStudentIdAndEventIdAndStatus(
            Long studentId, Long eventId, String status);
}
