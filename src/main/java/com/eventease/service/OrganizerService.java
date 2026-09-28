package com.eventease.service;

import com.eventease.entity.Organizer;
import com.eventease.exception.ResourceNotFoundException;
import com.eventease.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Service class for Organizer - contains all business logic related to organizers
@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    // Get all organizers from the database
    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    // Get a single organizer by ID
    // Throws ResourceNotFoundException if organizer does not exist
    public Organizer getOrganizerById(Long id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organizer not found with id: " + id));
    }

    // Create a new organizer and save to database
    public Organizer createOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    // Update an existing organizer's information
    public Organizer updateOrganizer(Long id, Organizer updatedOrganizer) {
        // First check if the organizer exists
        Organizer existingOrganizer = getOrganizerById(id);

        // Update the fields
        existingOrganizer.setName(updatedOrganizer.getName());
        existingOrganizer.setEmail(updatedOrganizer.getEmail());

        // Save and return the updated organizer
        return organizerRepository.save(existingOrganizer);
    }

    // Delete an organizer from the database
    public void deleteOrganizer(Long id) {
        // Check if organizer exists before deleting
        Organizer organizer = getOrganizerById(id);
        organizerRepository.delete(organizer);
    }
}
