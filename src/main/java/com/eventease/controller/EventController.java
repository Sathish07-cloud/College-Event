package com.eventease.controller;

import com.eventease.entity.Event;
import com.eventease.entity.Organizer;
import com.eventease.service.EventService;
import com.eventease.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

// Thymeleaf Controller for Event web pages
@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;

    // GET /events - Show all events
    // Optional filter by category: /events?category=Technical
    @GetMapping("/events")
    public String showEvents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Event> events;

        if (category != null && !category.isEmpty()) {
            // Filter by category
            events = eventService.getEventsByCategory(category);
        } else if (keyword != null && !keyword.isEmpty()) {
            // Search by keyword in title
            events = eventService.searchEventsByTitle(keyword);
        } else {
            // Show all events
            events = eventService.getAllEvents();
        }

        // Set available seats on each event
        java.util.Map<Long, Integer> availableSeatsMap = new java.util.HashMap<>();
        for (Event event : events) {
            int available = eventService.getAvailableSeats(event.getId());
            event.setAvailableSeats(available);
            availableSeatsMap.put(event.getId(), available);
        }

        model.addAttribute("events", events);
        model.addAttribute("category", category);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("availableSeatsMap", availableSeatsMap);

        return "events";
    }

    // GET /events/{id} - Show event details
    @GetMapping("/events/{id}")
    public String showEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        int availableSeats = eventService.getAvailableSeats(id);
        event.setAvailableSeats(availableSeats);

        model.addAttribute("event", event);
        model.addAttribute("availableSeats", availableSeats);

        return "event-details";
    }

    // GET /events/create - Show create event form
    @GetMapping("/events/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("organizers", organizerService.getAllOrganizers());
        return "create-event";
    }

    // POST /events/create - Submit create event form
    @PostMapping("/events/create")
    public String createEvent(
            @Valid @ModelAttribute("event") Event event,
            BindingResult result,
            @RequestParam(required = false) Long organizerId,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("organizers", organizerService.getAllOrganizers());
            return "create-event";
        }

        // Set the organizer for this event
        if (organizerId != null) {
            Organizer organizer = organizerService.getOrganizerById(organizerId);
            event.setOrganizer(organizer);
        } else if (event.getOrganizer() != null && event.getOrganizer().getId() != null) {
            Organizer organizer = organizerService.getOrganizerById(event.getOrganizer().getId());
            event.setOrganizer(organizer);
        }

        eventService.createEvent(event);
        redirectAttributes.addFlashAttribute("successMessage", "Event created successfully!");
        return "redirect:/events";
    }

    // GET /events/edit/{id} - Show edit event form
    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("organizers", organizerService.getAllOrganizers());
        return "edit-event";
    }

    // POST /events/edit/{id} - Submit edit event form
    @PostMapping("/events/edit/{id}")
    public String editEvent(
            @PathVariable Long id,
            @Valid @ModelAttribute("event") Event event,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("organizers", organizerService.getAllOrganizers());
            return "edit-event";
        }

        eventService.updateEvent(id, event);
        redirectAttributes.addFlashAttribute("successMessage", "Event updated successfully!");
        return "redirect:/organizer/events";
    }

    // POST /events/delete/{id} - Delete an event
    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventService.deleteEvent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Event deleted successfully!");
        return "redirect:/organizer/events";
    }

    // GET /organizer/events - Show organizer dashboard with dropdown or default
    @GetMapping("/organizer/events")
    public String showOrganizerPortal(
            @RequestParam(required = false) Long organizerId,
            Model model) {

        List<Organizer> organizers = organizerService.getAllOrganizers();
        model.addAttribute("organizers", organizers);

        List<Event> events;
        Organizer currentOrganizer = null;

        if (organizerId != null) {
            events = eventService.getEventsByOrganizer(organizerId);
            currentOrganizer = organizerService.getOrganizerById(organizerId);
            model.addAttribute("selectedOrganizerId", organizerId);
        } else {
            events = eventService.getAllEvents();
        }

        for (Event event : events) {
            int available = eventService.getAvailableSeats(event.getId());
            event.setAvailableSeats(available);
        }

        model.addAttribute("currentOrganizer", currentOrganizer);
        model.addAttribute("events", events);

        return "organizer-events";
    }

    // GET /organizer/{organizerId}/events - Show events by organizer
    @GetMapping("/organizer/{organizerId}/events")
    public String showOrganizerEvents(@PathVariable Long organizerId, Model model) {
        return "redirect:/organizer/events?organizerId=" + organizerId;
    }

    // GET /events/{eventId}/participants - Show participants for an event
    @GetMapping("/events/{eventId}/participants")
    public String showParticipants(@PathVariable Long eventId, Model model) {
        return "redirect:/events/" + eventId + "/registrations";
    }
}

// REST API Controller for Events
@RestController
@RequestMapping("/api/events")
class EventRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody Event event,
            @RequestParam Long organizerId) {
        Organizer organizer = organizerService.getOrganizerById(organizerId);
        event.setOrganizer(organizer);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully.");
    }
}
