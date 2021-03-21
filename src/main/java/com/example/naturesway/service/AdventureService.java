package com.example.naturesway.service;

import com.example.naturesway.domain.serviceModels.AdventureServiceModel;

import java.util.Collection;

public interface AdventureService {
    void addAdventure(AdventureServiceModel adventureServiceModel);

    Collection<AdventureServiceModel> findAll();

    void deleteAdventureById(java.lang.String id);
}
