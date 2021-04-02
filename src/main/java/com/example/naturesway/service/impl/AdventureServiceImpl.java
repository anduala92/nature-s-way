package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.error.AdventureAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.AdventureRepository;
import com.example.naturesway.service.AdventureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.naturesway.constants.Constants.*;

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
        checkIfAdventureExistByName(adventure.getName());
        adventureRepository.save(adventure);
    }

    @Override
    public Collection<AdventureServiceModel> findAll() {
        return adventureRepository.findAll()
                .stream()
                .map(this::getAdventureServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdventureById(String id) {
        adventureRepository.deleteById(id);
    }

    @Override
    public AdventureServiceModel findById(String id) {
        return mapper.map(this.getAdventureById(id), AdventureServiceModel.class);

    }

    @Override
    public void editById(String id, AdventureServiceModel adventureServiceModel) {
        Adventure adventure = this.getAdventureById(id);

        adventure.setName(adventureServiceModel.getName());
        adventure.setCategory(adventureServiceModel.getCategory());
        adventure.setCountry(adventureServiceModel.getCountry());
        adventure.setLevel(adventureServiceModel.getLevel());
        adventure.setTips(adventureServiceModel.getTips());
        adventure.setRequiredEquipment(adventureServiceModel.getRequiredEquipment());
        adventure.setDuration(adventureServiceModel.getDuration());
        adventure.setDescription(adventureServiceModel.getDescription());

        adventureRepository.save(adventure);
    }

    @Override
    public void saveAdventure(AdventureServiceModel adventureServiceModel) {
        adventureRepository.save(mapper.map(adventureServiceModel,Adventure.class));
    }

    private AdventureServiceModel getAdventureServiceModel(Adventure adventure) {
        AdventureServiceModel serviceModel = mapper.map(adventure, AdventureServiceModel.class);

        serviceModel.setCategory(adventure.getCategory());

        return serviceModel;
    }

    private Adventure getAdventureById(String id){
        return adventureRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(INCORRECT_ID));
    }

    private void checkIfAdventureExistByName(String adventureName) {
        Adventure adventureInDb = adventureRepository.findByName(adventureName).orElse(null);

        if (adventureInDb != null) {
            throw new AdventureAlreadyExistException(DUPLICATE_ADVENTURE);
        }

    }
}
