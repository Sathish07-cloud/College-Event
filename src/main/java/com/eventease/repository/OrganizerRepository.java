package com.eventease.repository;

import com.eventease.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository for Organizer - provides database operations for Organizer entity
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    // Find an organizer by their email address
    Optional<Organizer> findByEmail(String email);
}
