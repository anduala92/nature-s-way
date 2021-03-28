package com.example.naturesway.domain.entities;

import com.example.naturesway.domain.enumerations.AdventureCategoryEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "adventures")
public class Adventure extends BaseEntity{
    private String name;
    private AdventureCategoryEnum category;
    private String country;
    private Integer level;
    private String tips;
    private String requiredEquipment;
    private String duration;
    private String description;
    private String imageUrl;
    private Set<User> users;

    public Adventure() {
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
     public AdventureCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(AdventureCategoryEnum category) {
        this.category = category;
    }

    @Column(name = "country", nullable = false)
    @Size(min = 3, max = 30)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "level", nullable = false)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "tips")
    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Column(name = "requred_equipment")
    public String getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(String requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    @Column(name = "duration", nullable = false)
    @Size(min = 3)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Column(name = "description", nullable = false)
    @Size(min = 3)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "adentures_users",
            joinColumns = @JoinColumn(
                    name = "adventure_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id",
                    referencedColumnName = "id"
            )
    )    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
