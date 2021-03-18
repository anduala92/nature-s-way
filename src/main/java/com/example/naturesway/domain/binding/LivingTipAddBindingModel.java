package com.example.naturesway.domain.binding;

import com.example.naturesway.domain.enumerations.LivingTipEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LivingTipAddBindingModel {
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;

    public LivingTipAddBindingModel() {
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
    public LivingTipEnum getCategory() {
        return category;
    }

    public void setCategory(LivingTipEnum category) {
        this.category = category;
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

    @NotNull
    @NotEmpty
    @Length(min = 3, message = "ggg")
    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }
}
