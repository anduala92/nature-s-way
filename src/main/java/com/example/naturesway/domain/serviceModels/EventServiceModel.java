package com.example.naturesway.domain.serviceModels;

import java.util.Date;
import java.util.Set;

public class EventServiceModel extends BaseServiceModel{
    private String name;
    private Date eventDate;
    private String location;
    private String programme;
    private Set<UserServiceModel> users;

    public EventServiceModel() {
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

    public Set<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserServiceModel> users) {
        this.users = users;
    }
}
