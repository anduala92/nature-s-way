package com.example.naturesway.domain.binding;

import com.example.naturesway.domain.enumerations.LivingTipEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.example.naturesway.constants.Constants.*;

public class LivingTipAddBindingModel {
    private String id;
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;

    public LivingTipAddBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @NotNull(message = NULL_LIVING_TIP_NAME_MESSAGE)
    @NotEmpty(message = EMPTY_LIVING_TIP_NAME_MESSAGE)
    @Length(min = 3, max = 30, message = INVALID_LIVING_TIP_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = NULL_LIVING_CATEGORY_MESSAGE)
    public LivingTipEnum getCategory() {
        return category;
    }

    public void setCategory(LivingTipEnum category) {
        this.category = category;
    }

    @NotNull(message = NULL_LIVING_TIP_DESCRIPTION_MESSAGE)
    @NotEmpty(message = EMPTY_LIVING_TIP_DESCRIPTION_MESSAGE)
    @Length(min = 3, message = INVALID_LIVING_TIP_DESCRIPTION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = NULL_LIVING_TIP_USABILITY_MESSAGE)
    @NotEmpty(message = EMPTY_LIVING_TIP_USABILITY_MESSAGE)
    @Length(min = 3, message = INVALID_LIVING_TIP_USABILITY_MESSAGE)
    public String getUsability() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability = usability;
    }
}
