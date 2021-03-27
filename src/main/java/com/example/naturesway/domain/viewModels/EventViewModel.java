package com.example.naturesway.domain.viewModels;

import com.example.naturesway.domain.serviceModels.UserServiceModel;

import java.util.Date;
import java.util.Set;

public class EventViewModel {
    private String id;
    private String name;
    private Date eventDate;
    private String location;
    private String programme;
    private Boolean favorite;
    private Set<UserServiceModel> users;


    public EventViewModel() {
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Set<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserServiceModel> users) {
        this.users = users;
    }
}
