package com.leandrobaroni2103.planner.core.services;

import com.leandrobaroni2103.planner.core.entities.Link;
import com.leandrobaroni2103.planner.core.entities.Trip;
import com.leandrobaroni2103.planner.core.records.LinkData;
import com.leandrobaroni2103.planner.core.records.LinkRequestPayload;
import com.leandrobaroni2103.planner.core.records.LinkResponse;
import com.leandrobaroni2103.planner.core.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {
  @Autowired
  private LinkRepository linkRepository;


  public LinkResponse registerLink(LinkRequestPayload request, Trip trip) {
    Link link = new Link(request.title(), request.url(), trip);
    this.linkRepository.save(link);
    return new LinkResponse(link.getId());
  }

  public List<LinkData> getAllLinksFromTrip(UUID tripId) {
    List<Link> links = this.linkRepository.findByTripId(tripId);

    return links.stream().map(link -> new LinkData(link.getTitle(), link.getUrl())).toList();
  }
}
