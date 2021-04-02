package com.example.naturesway.service;

import com.example.naturesway.domain.serviceModels.EventServiceModel;

import java.util.List;

public interface EventService {
    void addEvent(EventServiceModel eventServiceModel);

    List<EventServiceModel> findAll();

    void deleteEventById(String id);

    EventServiceModel findById(String id);

    void editById(String id, EventServiceModel eventServiceModel);

    void updateEvent(EventServiceModel eventServiceModel);

}
