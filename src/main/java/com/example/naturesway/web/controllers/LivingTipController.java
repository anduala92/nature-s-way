package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.EventBindingModel;
import com.example.naturesway.domain.binding.LivingTipAddBindingModel;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.domain.viewModels.AdventureViewModel;
import com.example.naturesway.domain.viewModels.LivingTipViewModel;
import com.example.naturesway.service.LivingTipService;
import com.example.naturesway.utils.CloudinaryService;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/living-tips")
public class LivingTipController extends BaseController{
    private final LivingTipService livingTipService;
    private final ModelMapper mapper;

    public LivingTipController(LivingTipService livingTipService, ModelMapper mapper) {
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
        modelAndView.addObject("livingTips", livingTips);

        return view("livingTip/living-tips", modelAndView);
    }

    @GetMapping ("/living-tips/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addToFavorites(@PathVariable String id){
        LivingTipServiceModel livingTipServiceModel = livingTipService.findById(id);

        livingTipServiceModel.setFavorite(true);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        livingTipServiceModel.setUsername(username);

        livingTipService.updateEvent(livingTipServiceModel);
        return redirect("/living-tips/living-tips");
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
}
