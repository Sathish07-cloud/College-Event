package com.eventease.service;

import com.eventease.entity.Event;
import com.eventease.entity.Registration;
import com.eventease.entity.Student;
import com.eventease.exception.BusinessException;
import com.eventease.exception.ResourceNotFoundException;
import com.eventease.repository.EventRepository;
import com.eventease.repository.RegistrationRepository;
import com.eventease.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Service class for Registration - contains the most important business logic
// Rules enforced here:
// 1. Student cannot register if event is full
// 2. Student cannot register twice for the same event
// 3. Only REGISTERED registrations count as occupied seats
// 4. Student can only cancel their own registration
// 5. Cancellation is not allowed after the event date
@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EventRepository eventRepository;

    // Register a student for an event
    public Registration registerStudent(Long studentId, Long eventId) {

        // Step 1: Find the student - throw error if not found
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        // Step 2: Find the event - throw error if not found
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event not found with id: " + eventId));

        // Step 3: Check if student already has a REGISTERED registration for this event
        // (CANCELLED registrations are allowed to register again)
        Optional<Registration> existingRegistration = registrationRepository
                .findByStudentIdAndEventIdAndStatus(studentId, eventId, "REGISTERED");

        if (existingRegistration.isPresent()) {
            // Student is already registered - reject duplicate
            throw new BusinessException("You are already registered for this event.");
        }

        // Step 4: Count REGISTERED registrations for this event
        int registeredCount = registrationRepository
                .findByEventIdAndStatus(eventId, "REGISTERED").size();

        // Step 5: Compare count with maxSeats
        if (registeredCount >= event.getMaxSeats()) {
            // Event is full - reject registration
            throw new BusinessException(
                    "Registration failed: No seats are available for this event.");
        }

        // Step 6: All checks passed - create the registration
        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus("REGISTERED");

        // Step 7: Save registration to database
        return registrationRepository.save(registration);
    }

    // Cancel a student's registration
    public Registration cancelRegistration(Long registrationId, Long studentId) {

        // Step 1: Find the registration - throw error if not found
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Registration not found with id: " + registrationId));

        // Step 2: Check that this registration belongs to the requesting student
        // A student can only cancel their own registration
        if (!registration.getStudent().getId().equals(studentId)) {
            throw new BusinessException(
                    "You can only cancel your own registration.");
        }

        // Step 3: Get the event date
        LocalDate eventDate = registration.getEvent().getEventDate();
        LocalDate today = LocalDate.now();

        // Step 4: Check if the event date has already passed
        if (today.isAfter(eventDate)) {
            throw new BusinessException(
                    "Registration cannot be cancelled after the event date.");
        }

        // Step 5: Change status from REGISTERED to CANCELLED
        // We do NOT delete the registration - we just change its status
        // This frees the seat for another student
        registration.setStatus("CANCELLED");

        // Step 6: Save the updated registration
        return registrationRepository.save(registration);
    }

    // Get all registrations made by a specific student
    public List<Registration> getRegistrationsByStudent(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }

    // Get all registrations for a specific event
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    // Get a single registration by ID
    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Registration not found with id: " + id));
    }
}
