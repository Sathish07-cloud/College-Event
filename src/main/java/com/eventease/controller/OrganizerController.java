package com.eventease.controller;

import com.eventease.entity.Organizer;
import com.eventease.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// REST Controller for Organizer API endpoints
@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    // GET /api/organizers - Get all organizers
    @GetMapping
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    // GET /api/organizers/{id} - Get organizer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
        Organizer organizer = organizerService.getOrganizerById(id);
        return ResponseEntity.ok(organizer);
    }

    // POST /api/organizers - Create a new organizer
    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@Valid @RequestBody Organizer organizer) {
        Organizer created = organizerService.createOrganizer(organizer);
        return ResponseEntity.ok(created);
    }

    // PUT /api/organizers/{id} - Update organizer
    @PutMapping("/{id}")
    public ResponseEntity<Organizer> updateOrganizer(
            @PathVariable Long id,
            @Valid @RequestBody Organizer organizer) {
        Organizer updated = organizerService.updateOrganizer(id, organizer);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/organizers/{id} - Delete organizer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteOrganizer(id);
        return ResponseEntity.ok("Organizer deleted successfully.");
    }
}
