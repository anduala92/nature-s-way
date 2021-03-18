package com.example.naturesway.domain.entities;

import com.example.naturesway.domain.enumerations.LivingTipEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "living_tips")
public class LivingTip extends BaseEntity{
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usage;

    public LivingTip() {
    }

    @Column(name = "name", unique = true, nullable = false)
    @Size(min = 3, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    public LivingTipEnum getCategory() {
        return category;
    }

    public void setCategory(LivingTipEnum category) {
        this.category = category;
    }

    @Column(name = "description", nullable = false)
    @Size(min = 3)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "usage", nullable = false)
    @Size(min = 3)
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
