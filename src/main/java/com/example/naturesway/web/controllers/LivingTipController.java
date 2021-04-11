package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.LivingTipAddBindingModel;
import com.example.naturesway.domain.entities.LivingTip;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.domain.serviceModels.UserServiceModel;
import com.example.naturesway.domain.viewModels.LivingTipViewModel;
import com.example.naturesway.repository.LivingTipRepository;
import com.example.naturesway.service.LivingTipService;
import com.example.naturesway.service.UserService;
import com.example.naturesway.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/living-tips")
public class LivingTipController extends BaseController{
    private final LivingTipRepository livingTipRepository;
    private final UserService userService;
    private final LivingTipService livingTipService;
    private final ModelMapper mapper;

    public LivingTipController(LivingTipRepository livingTipRepository, UserService userService, LivingTipService livingTipService, ModelMapper mapper) {
        this.livingTipRepository = livingTipRepository;
        this.userService = userService;
        this.livingTipService = livingTipService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Living Tip")
    public ModelAndView addLivingTip(@ModelAttribute(name = "model") LivingTipAddBindingModel model) {
        return view("livingTip/add-living-tip");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addLivingTipConform(@Valid @ModelAttribute(name = "model") LivingTipAddBindingModel model,
                                          BindingResult bindingResult) throws IOException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            return view("/livingTip/add-living-tip");
        }
        LivingTipServiceModel livingTipServiceModel = mapper.map(model, LivingTipServiceModel.class);

        livingTipService.addLivingTip(livingTipServiceModel);

        return redirect("/living-tips/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Living Tips")
    public ModelAndView allAdventures(ModelAndView modelAndView) {
        List<LivingTipViewModel> livingTips = livingTipService.findAll()
                .stream()
                .map(livingTip -> mapper.map(livingTip, LivingTipViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("livingTips", livingTips);

        return view("livingTip/all-living-tip", modelAndView);
    }

    @GetMapping("/living-tips")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Living Tips")
    public ModelAndView livingTips(ModelAndView modelAndView) {
        List<LivingTipViewModel> livingTips = livingTipService.findAll()
                .stream()
                .map(livingTip -> mapper.map(livingTip, LivingTipViewModel.class))
                .collect(Collectors.toList());

        String username = getCurrentUser();
        for (LivingTipViewModel livingTip : livingTips) {
            for (UserServiceModel user : livingTip.getUsers()) {
                if (user.getUsername().equals(username)){
                    livingTip.setFavorite(true);
                }
            }
        }
        modelAndView.addObject("livingTips", livingTips);

        return view("livingTip/living-tips", modelAndView);
    }

    @GetMapping ("/living-tips/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addToFavorites(@PathVariable String id){
        LivingTipServiceModel livingTipServiceModel = livingTipService.findById(id);
        String username = getCurrentUser();
        UserServiceModel userServiceModel = userService.findUserByUsername(username);
        livingTipServiceModel.getUsers().add(userServiceModel);
        livingTipRepository.save(mapper.map(livingTipServiceModel, LivingTip.class));
        return redirect("/living-tips/living-tips");
    }

    @GetMapping("/favorites")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Favorite Events")
    public ModelAndView favoritesAdventures(ModelAndView modelAndView) {
        String username = getCurrentUser();

        List<LivingTipViewModel> livingTips = livingTipService.findAll()
                .stream()
                .map(livingTip -> mapper.map(livingTip, LivingTipViewModel.class))
                .collect(Collectors.toList());

        List<LivingTipViewModel> favoriteLivingTips = new ArrayList<>();

        for (LivingTipViewModel livingTipViewModel : livingTips) {
            for (UserServiceModel livingTipUser : livingTipViewModel.getUsers()) {
                if (livingTipUser.getUsername().equals(username)){
                    favoriteLivingTips.add(livingTipViewModel);
                }
            }
        }
        modelAndView.addObject("livingTips", favoriteLivingTips);

        return view("favorites/living-tips", modelAndView);
    }

    @GetMapping ("/favorites/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView removeFromFavorites(@PathVariable String id){
        LivingTipServiceModel livingTipServiceModel = livingTipService.findById(id);

        String username = getCurrentUser();

        Set<UserServiceModel> filteredUsers = new HashSet<>();
        for (UserServiceModel user : livingTipServiceModel.getUsers()) {
            if (!user.getUsername().equals(username)){
                filteredUsers.add(user);
            }
        }
        livingTipServiceModel.setUsers(filteredUsers);
        livingTipRepository.save(mapper.map(livingTipServiceModel, LivingTip.class));
        return redirect("/living-tips/favorites");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Living Tip")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView){
        LivingTipServiceModel livingTipServiceModel = livingTipService.findById(id);

        modelAndView.addObject("livingTip", mapper.map(livingTipServiceModel, LivingTipAddBindingModel.class));

        return view("livingTip/edit-living-tip", modelAndView);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @Valid @ModelAttribute(name = "livingTip") LivingTipAddBindingModel livingTipAddBindingModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView){
        if (bindingResult.hasErrors()){
            return view("livingTip/edit-living-tip", modelAndView);
        }
        LivingTipServiceModel livingTipServiceModel = mapper.map(livingTipAddBindingModel, LivingTipServiceModel.class);
        livingTipService.editById(id, livingTipServiceModel);
        return redirect("/living-tips/all");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        livingTipService.deleteLivingTipById(id);
        return redirect("/living-tips/all");
    }

    private String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
