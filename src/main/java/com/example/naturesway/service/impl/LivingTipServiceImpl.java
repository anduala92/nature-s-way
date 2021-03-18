package com.example.naturesway.service.impl;

import com.example.naturesway.domain.entities.Adventure;
import com.example.naturesway.domain.entities.LivingTip;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.repository.LivingTipRepository;
import com.example.naturesway.service.LivingTipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        livingTipRepository.save(livingTip);
    }

    @Override
    public List<LivingTipServiceModel> findAll() {
        return livingTipRepository.findAll()
                .stream()
                .map(this::getLivingTipServiceModel)
                .collect(Collectors.toList());
    }

    private LivingTipServiceModel getLivingTipServiceModel(LivingTip livingTip) {
        LivingTipServiceModel serviceModel = mapper.map(livingTip, LivingTipServiceModel.class);

        serviceModel.setCategory(livingTip.getCategory());
        return serviceModel;
    }
}
