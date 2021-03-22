package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.UserChangePasswordBindingModel;
import com.example.naturesway.domain.binding.UserEditBindingModel;
import com.example.naturesway.domain.binding.UserRegisterBindingModel;
import com.example.naturesway.domain.serviceModels.RoleServiceModel;
import com.example.naturesway.domain.serviceModels.UserServiceModel;
import com.example.naturesway.domain.viewModels.UserAllViewModel;
import com.example.naturesway.domain.viewModels.UserProfileViewModel;
import com.example.naturesway.service.UserService;
import com.example.naturesway.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register User ")
    public ModelAndView register(@ModelAttribute(name = "model") UserRegisterBindingModel model){
        return view("user/register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@Valid @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                        BindingResult bindingResult){
        if(this.passwordsNotMatch(model.getPassword(), model.getConfirmPassword())){
            bindingResult.addError(new FieldError("model", "password", "Passwords don't match."));
        }
        if (bindingResult.hasErrors()) {
            return view("user/register");
        }
        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.registerUser(serviceModel);

        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login User")
    public ModelAndView login(){
        return view("user/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserProfileViewModel model = mapper.map(user, UserProfileViewModel.class);
        modelAndView.addObject("model", model);

        return view("user/profile", modelAndView);
    }

    @GetMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit User")
    public ModelAndView editProfile(Principal principal,
                                    ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserEditBindingModel model = mapper.map(user, UserEditBindingModel.class);
        modelAndView.addObject("model", model);

        return view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "model") UserEditBindingModel model,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return view("user/edit-profile");
        }

        //TODO add check if email is existing set variable and show message
        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.editUserProfile(serviceModel,null, model.getEmail());

        return redirect("/users/profile");
    }

    @GetMapping("/edit-change-password")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit User")
    public ModelAndView changePassword(Principal principal,
                                    ModelAndView modelAndView){
        UserServiceModel user = userService.findUserByUsername(principal.getName());
        UserChangePasswordBindingModel model = mapper.map(user, UserChangePasswordBindingModel.class);
        modelAndView.addObject("model", model);

        return view("user/edit-change-password", modelAndView);
    }

    @PostMapping("/edit-change-password")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "model") UserChangePasswordBindingModel model,
                                           BindingResult bindingResult){
        if(this.passwordsNotMatch(model.getPassword(), model.getConfirmPassword())){
            bindingResult.addError(new FieldError("model", "password", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            return view("user/edit-change-password");
        }
        UserServiceModel user = userService.findUserByUsername(model.getUsername());
        user.setPassword(model.getPassword());

        UserServiceModel serviceModel = mapper.map(user, UserServiceModel.class);
        userService.editUserProfile(serviceModel, model.getOldPassword(), null);

        return redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Users")
    public ModelAndView allUsers(ModelAndView modelAndView){
        //todo make better view fix search and count with margin left top and margin right top
        List<UserAllViewModel> users = userService.findAll()
                .stream()
                .map(u -> {
                    UserAllViewModel user = mapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = getAuthoritiesToString(u);
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return view("user/all-users", modelAndView);
    }

    @GetMapping("/all-delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Users")
    public ModelAndView allDeleteUsers(ModelAndView modelAndView){
        List<UserAllViewModel> users = userService.findAll()
                .stream()
                .map(u -> {
                    UserAllViewModel user = mapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = getAuthoritiesToString(u);
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return view("user/all-delete-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        userService.setUserRole(id, "user");

        return redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        userService.setUserRole(id, "moderator");

        return redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        userService.setUserRole(id, "admin");

        return redirect("/users/all");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        userService.deleteUserById(id);
        return redirect("/users/all");
    }

    private Set<String> getAuthoritiesToString(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(RoleServiceModel::getAuthority)
                .collect(Collectors.toSet());
    }

    private boolean passwordsNotMatch(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }
}
