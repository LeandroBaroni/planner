package com.leandrobaroni2103.planner.core.controllers;

import com.leandrobaroni2103.planner.core.entities.Participant;
import com.leandrobaroni2103.planner.core.entities.Trip;
import com.leandrobaroni2103.planner.core.records.ParticipantRequestPayload;
import com.leandrobaroni2103.planner.core.records.TripCreateResponsePayload;
import com.leandrobaroni2103.planner.core.records.TripRequestPayload;
import com.leandrobaroni2103.planner.core.repositories.TripRepository;
import com.leandrobaroni2103.planner.core.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private TripRepository tripRepository;

  @PostMapping("/")
  public ResponseEntity<TripCreateResponsePayload> createTrip(@RequestBody TripRequestPayload request) {
    Trip trip = new Trip(request);

    this.tripRepository.save(trip);

    this.participantService.registerParticipantsToEvent(request.emails_to_invite(), trip);

    return ResponseEntity.status(201).body(new TripCreateResponsePayload(trip.getId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
    Optional<Trip> trip = this.tripRepository.findById(id);
    return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload request) {
    Optional<Trip> trip = this.tripRepository.findById(id);

    if(trip.isPresent()) {
      Trip updatedTrip = trip.get();
      updatedTrip.setDestination(request.destination());
      updatedTrip.setStartsAt(LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
      updatedTrip.setEndsAt(LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME));

      this.tripRepository.save(updatedTrip);
      return ResponseEntity.ok(updatedTrip);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("{id}/confirm")
  public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){
    Optional<Trip> trip = this.tripRepository.findById(id);
    if(trip.isPresent()) {
      Trip updatedTrip = trip.get();
      updatedTrip.setIsConfirmed(true);
      this.tripRepository.save(updatedTrip);
      this.participantService.triggerConfirmationEmailToParticipants(id);
      return ResponseEntity.status(200).body(updatedTrip);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/{id}/invite")
  public ResponseEntity<UUID> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload request) {
    Optional<Trip> trip = this.tripRepository.findById(id);

    if(trip.isPresent()) {
      Trip updatedTrip = trip.get();

      UUID participantId = this.participantService.registerParticipantToEvent(request.email(), updatedTrip);
      if(updatedTrip.getIsConfirmed()){
        this.participantService.triggerConfirmationEmailToParticipant(request.email());
      }
      return ResponseEntity.status(201).body(participantId);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("{id}/participants")
  public ResponseEntity<List<Participant>> getAllParticipants(@PathVariable UUID id) {
    Optional<Trip> trip = this.tripRepository.findById(id);

    if(trip.isPresent()) {
      List<Participant> participants = this.participantService.getAllParticipantsFromTrip(id);
      return ResponseEntity.status(200).body(participants);
    }

    return ResponseEntity.notFound().build();
  }
}
