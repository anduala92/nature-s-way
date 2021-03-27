package com.example.naturesway.domain.serviceModels;

import com.example.naturesway.domain.enumerations.LivingTipEnum;

import java.util.Set;

public class LivingTipServiceModel extends BaseServiceModel{
    private String name;
    private LivingTipEnum category;
    private String description;
    private String usability;
    private Set<UserServiceModel> users;

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

    public Set<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserServiceModel> users) {
        this.users = users;
    }
}
