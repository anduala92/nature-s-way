package com.example.naturesway.service;

import com.example.naturesway.domain.serviceModels.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword, String viewDtoEmail);

    List<UserServiceModel> findAll();

    void setUserRole(String id, String role);
}
