package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.repository.AdventureRepository;
import com.example.naturesway.service.AdventureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdventureServiceImpl implements AdventureService {
    private final ModelMapper mapper;
    private final AdventureRepository adventureRepository;

    public AdventureServiceImpl(ModelMapper mapper, AdventureRepository adventureRepository) {
        this.mapper = mapper;
        this.adventureRepository = adventureRepository;
    }

    @Override
    public void addAdventure(AdventureServiceModel adventureServiceModel) {
        Adventure adventure = mapper.map(adventureServiceModel,Adventure.class);
        adventureRepository.save(adventure);
    }

    @Override
    public Collection<AdventureServiceModel> findAll() {
        return adventureRepository.findAll()
                .stream()
                .map(this::getAdventureServiceModel)
                .collect(Collectors.toList());
    }

    private AdventureServiceModel getAdventureServiceModel(Adventure adventure) {
        AdventureServiceModel serviceModel = mapper.map(adventure, AdventureServiceModel.class);

        serviceModel.setCategory(adventure.getCategory());

        return serviceModel;
    }
}
