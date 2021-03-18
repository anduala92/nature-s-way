package com.example.naturesway.domain.serviceModels;

import com.example.naturesway.domain.enumerations.AdventureCategoryEnum;
import org.springframework.web.multipart.MultipartFile;

public class AdventureServiceModel {
    private String name;
    private AdventureCategoryEnum category;
    private String country;
    private Integer level;
    private String tips;
    private String requiredEquipment;
    private String duration;
    private String description;
    private String imageUrl;

    public AdventureServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdventureCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(AdventureCategoryEnum category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(String requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
