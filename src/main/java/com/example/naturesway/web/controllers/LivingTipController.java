package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.LivingTipAddBindingModel;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.domain.viewModels.AdventureViewModel;
import com.example.naturesway.domain.viewModels.LivingTipViewModel;
import com.example.naturesway.service.LivingTipService;
import com.example.naturesway.utils.CloudinaryService;
import com.example.naturesway.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
