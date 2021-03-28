package com.example.naturesway.domain.binding;

import com.example.naturesway.domain.enumerations.AdventureCategoryEnum;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import static com.example.naturesway.constants.Constants.*;

public class AdventureAddBindingModel {
    private String id;
    private String name;
    private AdventureCategoryEnum category;
    private String country;
    private Integer level;
    private String tips;
    private String requiredEquipment;
    private String duration;
    private String description;
    private MultipartFile image;

    public AdventureAddBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = NULL_ADVENTURE_NAME_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_NAME_MESSAGE)
    @Length(min = 3, max = 30, message = INVALID_ADVENTURE_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public AdventureCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(AdventureCategoryEnum category) {
        this.category = category;
    }

    @NotNull(message = NULL_ADVENTURE_CATEGORY_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_CATEGORY_MESSAGE)
    @Length(min = 3, max = 30, message = INVALID_ADVENTURE_CATEGORY_MESSAGE)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull(message = NULL_ADVENTURE_LEVEL_MESSAGE)
    @Range(min = 1,max = 3,message = INVALID_ADVENTURE_LEVEL_MESSAGE)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @NotNull(message = NULL_ADVENTURE_TIPS_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_TIPS_MESSAGE)
    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @NotNull(message = NULL_ADVENTURE_REQUIRED_EQUIPMENT_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_REQUIRED_EQUIPMENT_MESSAGE)
    public String getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(String requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    @NotNull(message = NULL_ADVENTURE_DURATION_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_DURATION_MESSAGE)
    @Length(min = 3, message = INVALID_ADVENTURE_DURATION_MESSAGE)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @NotNull(message = NULL_ADVENTURE_DESCRIPTION_MESSAGE)
    @NotEmpty(message = EMPTY_ADVENTURE_DESCRIPTION_MESSAGE)
    @Length(min = 3, message = INVALID_ADVENTURE_DESCRIPTION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
