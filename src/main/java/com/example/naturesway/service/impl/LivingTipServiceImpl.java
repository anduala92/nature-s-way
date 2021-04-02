package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.LivingTip;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.error.LivingTipAlreadyExistException;
import com.example.naturesway.error.RecordNotFoundException;
import com.example.naturesway.repository.LivingTipRepository;
import com.example.naturesway.service.LivingTipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.naturesway.constants.Constants.*;

@Service
public class LivingTipServiceImpl implements LivingTipService {
    private final ModelMapper mapper;
    private final LivingTipRepository livingTipRepository;

    public LivingTipServiceImpl(ModelMapper mapper, LivingTipRepository livingTipRepository) {
        this.mapper = mapper;
        this.livingTipRepository = livingTipRepository;
    }

    @Override
    public void addLivingTip(LivingTipServiceModel livingTipServiceModel) {
        LivingTip livingTip = mapper.map(livingTipServiceModel, LivingTip.class);
        checkIfLivingTipExistByName(livingTip.getName());
        livingTipRepository.save(livingTip);
    }

    @Override
    public List<LivingTipServiceModel> findAll() {
        return livingTipRepository.findAll()
                .stream()
                .map(this::getLivingTipServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLivingTipById(String id) {
        livingTipRepository.deleteById(id);
    }

    @Override
    public LivingTipServiceModel findById(String id) {
        return mapper.map(this.getLivingTipId(id),LivingTipServiceModel.class);

    }

    @Override
    public void editById(String id, LivingTipServiceModel livingTipServiceModel) {
        LivingTip livingTip = this.getLivingTipId(id);

        livingTip.setName(livingTipServiceModel.getName());
        livingTip.setCategory(livingTipServiceModel.getCategory());
        livingTip.setUsability(livingTipServiceModel.getUsability());
        livingTip.setDescription(livingTipServiceModel.getDescription());

        livingTipRepository.save(livingTip);
    }

    @Override
    public void updateLivingTip(LivingTipServiceModel livingTipServiceModel) {
        livingTipRepository.save(mapper.map(livingTipServiceModel, LivingTip.class));
    }

    private LivingTipServiceModel getLivingTipServiceModel(LivingTip livingTip) {
        LivingTipServiceModel serviceModel = mapper.map(livingTip, LivingTipServiceModel.class);

        serviceModel.setCategory(livingTip.getCategory());
        return serviceModel;
    }

    private LivingTip getLivingTipId(String id){
        return livingTipRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(INCORRECT_ID));
    }

    private void checkIfLivingTipExistByName(String livingTipName) {
        LivingTip livingTipInDb = livingTipRepository.findByName(livingTipName).orElse(null);

        if (livingTipInDb != null) {
            throw new LivingTipAlreadyExistException(DUPLICATE_LIVING_TIP);
        }
    }
}
