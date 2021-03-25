package com.example.naturesway.domain.viewModels;

import com.example.naturesway.domain.enumerations.LivingTipEnum;

public class LivingTipViewModel {
    private String id;
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;
    private String username;
    private Boolean favorite;

    public LivingTipViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
