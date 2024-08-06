package com.leandrobaroni2103.planner.core.repositories;

import com.leandrobaroni2103.planner.core.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
  public List<Link> findByTripId(UUID id);
}
