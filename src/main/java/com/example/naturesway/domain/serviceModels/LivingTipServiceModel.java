package com.example.naturesway.domain.serviceModels;

import com.example.naturesway.domain.enumerations.LivingTipEnum;

public class LivingTipServiceModel {
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;

    public LivingTipServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LivingTipEnum getCategory() {
        return category;
    }

    public void setCategory(LivingTipEnum category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }
}
