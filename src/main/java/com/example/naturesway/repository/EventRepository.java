package com.example.naturesway.repository;

import com.example.naturesway.domain.entities.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {
    Optional<Event> findByName(String eventName);

}
