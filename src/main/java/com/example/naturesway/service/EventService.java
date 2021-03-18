package com.example.naturesway.service;

import com.example.naturesway.domain.serviceModels.EventServiceModel;

import java.util.Collection;
import java.util.List;

public interface EventService {
    void addEvent(EventServiceModel eventServiceModel);

    List<EventServiceModel> findAll();
}
