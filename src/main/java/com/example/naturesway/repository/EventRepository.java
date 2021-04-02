package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.Event;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {
    Optional<Event> findByName(String eventName);

    @Query(value = "SELECT e FROM Event e WHERE e.eventDate < ?1")
    List<Event> findAllPastEvents(Date now);
}
