package com.example.naturesway.service;

import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;

import java.util.Collection;
import java.util.List;

public interface LivingTipService {
    void addLivingTip(LivingTipServiceModel livingTipServiceModel);

    List<LivingTipServiceModel> findAll();
}
