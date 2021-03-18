package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.entities.Event;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.repository.EventRepository;
import com.example.naturesway.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final ModelMapper mapper;
    private final EventRepository eventRepository;

    public EventServiceImpl(ModelMapper mapper, EventRepository eventRepository) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(EventServiceModel eventServiceModel) {
        Event event = mapper.map(eventServiceModel,Event.class);
        eventRepository.save(event);
    }

    @Override
    public List<EventServiceModel> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::getEventServiceModel)
                .collect(Collectors.toList());
    }

    private EventServiceModel getEventServiceModel(Event event) {
        return mapper.map(event, EventServiceModel.class);
    }
}
