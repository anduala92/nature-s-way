package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.Event;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.error.EventAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.EventRepository;
import com.example.naturesway.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.naturesway.constants.Constants.*;

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
        checkIfEventExistByName(event.getName());
        eventRepository.save(event);
    }

    @Override
    public List<EventServiceModel> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::getEventServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEventById(String id) {
        eventRepository.deleteById(id);
    }

    @Override
    public EventServiceModel findById(String id) {
        return mapper.map(this.getEventById(id),EventServiceModel.class);
    }

    @Override
    public void editById(String id, EventServiceModel eventServiceModel) {
        Event event = this.getEventById(id);

        event.setName(eventServiceModel.getName());
        event.setEventDate(eventServiceModel.getEventDate());
        event.setLocation(eventServiceModel.getLocation());
        event.setProgramme(eventServiceModel.getProgramme());

        eventRepository.save(event);
    }

    @Scheduled(cron = "@midnight")
    private void deletePastEvents() {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        try {
            date = ft.parse(LocalDate.now().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        eventRepository.deleteAll(eventRepository.findAllPastEvents(date));
    }

    private EventServiceModel getEventServiceModel(Event event) {
        return mapper.map(event, EventServiceModel.class);
    }

    private Event getEventById(String id){
        return eventRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(INCORRECT_ID));
    }

    private void checkIfEventExistByName(String eventName) {
        Event eventInDb = eventRepository.findByName(eventName).orElse(null);

        if (eventInDb != null) {
            throw new EventAlreadyExistException(DUPLICATE_EVENT);
        }
    }
}