package com.leandrobaroni2103.planner.core.controllers;

import com.leandrobaroni2103.planner.core.entities.Participant;
import com.leandrobaroni2103.planner.core.records.ParticipantRequestPayload;
import com.leandrobaroni2103.planner.core.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

  @Autowired
  private ParticipantRepository participantRepository;

  @PostMapping("/{id}/confirm")
  public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload request) {

    Optional<Participant> participant = this.participantRepository.findById(id);

    if(participant.isPresent()) {
      Participant newParticipant = participant.get();

      newParticipant.setIsConfirmed(true);
      newParticipant.setName(request.name());

      this.participantRepository.save(newParticipant);
      return ResponseEntity.ok(newParticipant);
    }

    return ResponseEntity.notFound().build();
  }


}
