package com.leandrobaroni2103.planner.core.services;

import com.leandrobaroni2103.planner.core.entities.Activity;
import com.leandrobaroni2103.planner.core.entities.Participant;
import com.leandrobaroni2103.planner.core.entities.Trip;
import com.leandrobaroni2103.planner.core.records.ActivityRequestPayload;
import com.leandrobaroni2103.planner.core.records.ActivityResponse;
import com.leandrobaroni2103.planner.core.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
  @Autowired
  private ActivityRepository activityRepository;

  public ActivityResponse registerActivity(ActivityRequestPayload request, Trip trip) throws Exception {
    Activity activity = new Activity(request.title(), request.occurs_at(), trip);

    if(activity.getOccursAt().isAfter(trip.getStartsAt()) && activity.getOccursAt().isBefore(trip.getEndsAt())) {
      this.activityRepository.save(activity);

      return new ActivityResponse(activity.getId());
    }

    throw new Exception("Data inválida");
  }

  public List<Activity> getAllActivitiesFromTrip(UUID tripId){
    List<Activity> activities = this.activityRepository.findByTripId(tripId);

    return activities;
  }
}
