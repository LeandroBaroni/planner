package com.leandrobaroni2103.planner.core.services;

import com.leandrobaroni2103.planner.core.entities.Participant;
import com.leandrobaroni2103.planner.core.entities.Trip;
import com.leandrobaroni2103.planner.core.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {
  @Autowired
  private ParticipantRepository participantRepository;

  public List<UUID> registerParticipantsToEvent(List<String> participantsToInvite, Trip trip) {
    List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

    this.participantRepository.saveAll(participants);

    List<UUID> ids = participants.stream().map(participant -> participant.getId()).toList();
    System.out.println(ids);
    return ids;
  }

  public UUID registerParticipantToEvent(String email, Trip trip) {
    Participant participant = new Participant(email, trip);

    this.participantRepository.save(participant);

    return participant.getId();
  }

  public void triggerConfirmationEmailToParticipants(UUID tripId) {
    System.out.println("tripId: " + tripId);
  }

  public void triggerConfirmationEmailToParticipant(String email) {
    System.out.println("email: " + email);
  }

  public List<Participant> getAllParticipantsFromTrip(UUID tripId) {
    List<Participant> participants = this.participantRepository.findByTripId(tripId);

    return participants;
  }
}
