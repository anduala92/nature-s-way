package com.example.naturesway.domain.binding;

import com.example.naturesway.domain.enumerations.AdventureCategoryEnum;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    //todo replace messages with static tets
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 30, message = "ggg")
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

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 30, message = "ggg")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull
    @Range(min = 1,max = 3,message = "Level must me be at less equals to 1.")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @NotNull
    @NotEmpty
    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @NotNull
    @NotEmpty
    public String getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(String requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 50, message = "ggg")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @NotNull
    @NotEmpty
    @Length(min = 3, message = "ggg")
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
