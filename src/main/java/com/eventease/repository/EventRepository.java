package com.eventease.repository;

import com.eventease.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository for Event - provides database operations for Event entity
public interface EventRepository extends JpaRepository<Event, Long> {

    // Find all events belonging to a specific organizer
    List<Event> findByOrganizerId(Long organizerId);

    // Find events by category (e.g., Technical, Cultural, Sports)
    List<Event> findByCategoryIgnoreCase(String category);

    // Find events whose title contains the search keyword (case-insensitive)
    List<Event> findByTitleContainingIgnoreCase(String keyword);
}
