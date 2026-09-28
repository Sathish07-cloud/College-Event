package com.eventease.controller;

import com.eventease.entity.Event;
import com.eventease.entity.Registration;
import com.eventease.exception.BusinessException;
import com.eventease.exception.ResourceNotFoundException;
import com.eventease.service.EventService;
import com.eventease.service.RegistrationService;
import com.eventease.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Thymeleaf Controller for Registration web pages
@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private StudentService studentService;

    // GET /register/{eventId} - Show registration form
    @GetMapping("/register/{eventId}")
    public String showRegistrationForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        int availableSeats = eventService.getAvailableSeats(eventId);
        event.setAvailableSeats(availableSeats);

        model.addAttribute("event", event);
        model.addAttribute("availableSeats", availableSeats);
        model.addAttribute("students", studentService.getAllStudents());

        return "register";
    }

    // POST /register/{eventId} - Submit registration
    @PostMapping("/register/{eventId}")
    public String registerStudent(
            @PathVariable Long eventId,
            @RequestParam Long studentId,
            Model model) {

        Event event = eventService.getEventById(eventId);
        int availableSeats = eventService.getAvailableSeats(eventId);
        event.setAvailableSeats(availableSeats);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("studentId", studentId);

        try {
            // Try to register the student
            registrationService.registerStudent(studentId, eventId);

            // Success
            int updatedSeats = eventService.getAvailableSeats(eventId);
            event.setAvailableSeats(updatedSeats);
            model.addAttribute("event", event);
            model.addAttribute("successMessage", "Registration successful!");
            model.addAttribute("availableSeats", updatedSeats);
            return "register";

        } catch (BusinessException ex) {
            // Business rule violated - show error message
            model.addAttribute("event", event);
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("availableSeats", availableSeats);
            return "register";

        } catch (ResourceNotFoundException ex) {
            // Student or Event not found
            model.addAttribute("event", event);
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("availableSeats", availableSeats);
            return "register";
        }
    }

    // GET /my-registrations - Show registrations lookup or specific student
    @GetMapping("/my-registrations")
    public String showRegistrationsPortal(
            @RequestParam(required = false) Long studentId,
            Model model) {

        model.addAttribute("students", studentService.getAllStudents());

        if (studentId != null) {
            try {
                model.addAttribute("student", studentService.getStudentById(studentId));
                model.addAttribute("studentId", studentId);
                model.addAttribute("registrations",
                        registrationService.getRegistrationsByStudent(studentId));
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }
        return "my-registrations";
    }

    // GET /student/{studentId}/registrations - Show student's registrations
    @GetMapping("/student/{studentId}/registrations")
    public String showMyRegistrations(@PathVariable Long studentId, Model model) {
        return "redirect:/my-registrations?studentId=" + studentId;
    }

    // POST /registration/{registrationId}/cancel - Cancel a registration
    @PostMapping("/registration/{registrationId}/cancel")
    public String cancelRegistration(
            @PathVariable Long registrationId,
            @RequestParam Long studentId,
            RedirectAttributes redirectAttributes) {

        try {
            registrationService.cancelRegistration(registrationId, studentId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Registration cancelled successfully.");
        } catch (BusinessException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/student/" + studentId + "/registrations";
    }

    // GET /events/{eventId}/registrations - Show event participants
    @GetMapping("/events/{eventId}/registrations")
    public String showEventRegistrations(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        int availableSeats = eventService.getAvailableSeats(eventId);
        event.setAvailableSeats(availableSeats);

        List<Registration> registrations = registrationService.getRegistrationsByEvent(eventId);

        model.addAttribute("event", event);
        model.addAttribute("availableSeats", availableSeats);
        model.addAttribute("registrations", registrations);

        return "participants";
    }
}

// REST API Controller for Registrations
@RestController
@RequestMapping("/api/registrations")
class RegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    // POST /api/registrations - Register a student for an event
    @PostMapping
    public ResponseEntity<Registration> registerStudent(
            @RequestParam Long studentId,
            @RequestParam Long eventId) {
        Registration registration = registrationService.registerStudent(studentId, eventId);
        return ResponseEntity.ok(registration);
    }

    // GET /api/registrations/{id} - Get a registration by ID
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationById(id));
    }

    // GET /api/registrations/student/{studentId} - Get registrations for a student
    @GetMapping("/student/{studentId}")
    public List<Registration> getRegistrationsByStudent(@PathVariable Long studentId) {
        return registrationService.getRegistrationsByStudent(studentId);
    }

    // GET /api/registrations/event/{eventId} - Get registrations for an event
    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable Long eventId) {
        return registrationService.getRegistrationsByEvent(eventId);
    }

    // PUT /api/registrations/{id}/cancel - Cancel a registration
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancelRegistration(
            @PathVariable Long id,
            @RequestParam Long studentId) {
        registrationService.cancelRegistration(id, studentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration cancelled successfully.");
        return ResponseEntity.ok(response);
    }
}
