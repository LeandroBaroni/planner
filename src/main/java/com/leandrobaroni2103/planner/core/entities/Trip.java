package com.leandrobaroni2103.planner.core.entities;

import com.leandrobaroni2103.planner.core.records.TripRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String destination;
  @Column(name = "starts_at", nullable = false)
  private LocalDateTime startsAt;
  @Column(name = "ends_at", nullable = false)
  private LocalDateTime endsAt;
  @Column(name = "is_confirmed", nullable = false)
  private Boolean isConfirmed;
  @Column(name = "owner_name", nullable = false)
  private String ownerName;
  @Column(name = "owner_email", nullable = false)
  private String ownerEmail;

  public Trip(TripRequestPayload request) {
    this.destination = request.destination();
    this.isConfirmed = false;
    this.ownerEmail = request.owner_email();
    this.ownerName = request.owner_name();
    this.startsAt = LocalDateTime.parse(request.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
    this.endsAt = LocalDateTime.parse(request.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
  }
}