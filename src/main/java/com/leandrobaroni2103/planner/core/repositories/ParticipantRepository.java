package com.leandrobaroni2103.planner.core.repositories;

import com.leandrobaroni2103.planner.core.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
  public List<Participant> findByTripId(UUID id);
}
