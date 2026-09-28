package com.eventease.service;

import com.eventease.entity.Event;
import com.eventease.entity.Organizer;
import com.eventease.exception.ResourceNotFoundException;
import com.eventease.repository.EventRepository;
import com.eventease.repository.OrganizerRepository;
import com.eventease.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Service class for Event - contains all business logic related to events
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // Get all events from the database
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get a single event by ID
    // Throws ResourceNotFoundException if event does not exist
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event not found with id: " + id));
    }

    // Get all events created by a specific organizer
    public List<Event> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    // Get events by category (e.g., Technical, Cultural)
    public List<Event> getEventsByCategory(String category) {
        return eventRepository.findByCategoryIgnoreCase(category);
    }

    // Search events by keyword in title
    public List<Event> searchEventsByTitle(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Update an existing event
    public Event updateEvent(Long id, Event updatedEvent) {
        // Check if event exists
        Event existingEvent = getEventById(id);

        // Update the fields
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setEventDate(updatedEvent.getEventDate());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setMaxSeats(updatedEvent.getMaxSeats());
        existingEvent.setCategory(updatedEvent.getCategory());

        // Save and return the updated event
        return eventRepository.save(existingEvent);
    }

    // Delete an event from the database
    public void deleteEvent(Long id) {
        // Check if event exists before deleting
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    // Calculate available seats for an event
    // Available seats = maxSeats - number of REGISTERED registrations
    // CANCELLED registrations do NOT count as occupied seats
    public int getAvailableSeats(Long eventId) {
        Event event = getEventById(eventId);

        // Count only REGISTERED (not CANCELLED) registrations
        int registeredCount = registrationRepository
                .findByEventIdAndStatus(eventId, "REGISTERED").size();

        // Calculate available seats
        int availableSeats = event.getMaxSeats() - registeredCount;

        return availableSeats;
    }
}
