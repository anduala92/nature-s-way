package com.example.naturesway.service;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;

import java.util.Collection;

public interface AdventureService {
    void addAdventure(AdventureServiceModel adventureServiceModel);

    Collection<AdventureServiceModel> findAll();

    void deleteAdventureById(java.lang.String id);

    AdventureServiceModel findById(String id);

    void editById(String id, AdventureServiceModel adventureServiceModel);

    void saveAdventure(AdventureServiceModel adventureServiceModel);

}
