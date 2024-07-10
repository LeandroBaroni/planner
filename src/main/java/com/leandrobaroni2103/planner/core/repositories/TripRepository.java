package com.leandrobaroni2103.planner.core.repositories;

import com.leandrobaroni2103.planner.core.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
  public Trip findByOwnerEmail(String ownerEmail);
}
