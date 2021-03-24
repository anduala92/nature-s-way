package com.example.naturesway.domain.entities;

import com.example.naturesway.domain.enumerations.LivingTipEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "living_tips")
public class LivingTip extends BaseEntity{
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;
    private String username;
    private Boolean favorite;

    public LivingTip() {
    }

    @Column(name = "name")
    @NotNull
    @Size(min = 3, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "category")
    @NotNull
    @Enumerated(EnumType.STRING)
    public LivingTipEnum getCategory() {
        return category;
    }

    public void setCategory(LivingTipEnum category) {
        this.category = category;
    }

    @Column(name = "description")
    @NotNull
    @Size(min = 3)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "usability")
    @NotNull
    @Size(min = 3)
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
