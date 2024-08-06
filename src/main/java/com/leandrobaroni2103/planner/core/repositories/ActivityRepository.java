package com.leandrobaroni2103.planner.core.repositories;

import com.leandrobaroni2103.planner.core.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
  public List<Activity> findByTripId(UUID id);
}
