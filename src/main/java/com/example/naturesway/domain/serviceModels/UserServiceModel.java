package com.example.naturesway.domain.serviceModels;

import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleServiceModel> authorities;
    private Set<AdventureServiceModel> adventures;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }

    public Set<AdventureServiceModel> getAdventures() {
        return adventures;
    }

    public void setAdventures(Set<AdventureServiceModel> adventures) {
        this.adventures = adventures;
    }
}
