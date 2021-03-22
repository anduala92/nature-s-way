package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.AdventureAddBindingModel;
import com.example.naturesway.domain.binding.LivingTipAddBindingModel;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.domain.viewModels.AdventureViewModel;
import com.example.naturesway.service.AdventureService;
import com.example.naturesway.utils.CloudinaryService;
import com.example.naturesway.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adventures")
public class AdventureController extends BaseController{
    private final AdventureService adventureService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;


    @Autowired
    public AdventureController(AdventureService adventureService, ModelMapper mapper, CloudinaryService cloudinaryService) {
        this.adventureService = adventureService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Adventure")
    public ModelAndView addAdventure(@ModelAttribute(name = "model") AdventureAddBindingModel model) {
        return view("adventure/add-adventure");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addAdventureConform(@Valid @ModelAttribute(name = "model") AdventureAddBindingModel model,
                                          BindingResult bindingResult) throws IOException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            return view("adventure/add-adventure");
        }

        AdventureServiceModel adventureServiceModel = mapper.map(model, AdventureServiceModel.class);
        adventureServiceModel.setImageUrl(
                this.cloudinaryService.uploadImage(model.getImage())
        );
        adventureService.addAdventure(adventureServiceModel);

        return redirect("/adventures/all");
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Adventures")
    public ModelAndView allAdventures(ModelAndView modelAndView) {
        List<AdventureViewModel> adventures = adventureService.findAll()
                .stream()
                .map(adventure -> mapper.map(adventure, AdventureViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("adventures", adventures);

        return view("adventure/all-adventure", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Adventure")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView){
        AdventureServiceModel adventureServiceModel = adventureService.findById(id);
        modelAndView.addObject("adventure", mapper.map(adventureServiceModel, AdventureAddBindingModel.class));
        return view("adventure/edit-adventure", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @Valid @ModelAttribute(name = "adventure") AdventureAddBindingModel adventureAddBindingModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView) throws IOException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            return view("adventure/edit-adventure", modelAndView);
        }
        AdventureServiceModel adventureServiceModel = mapper.map(adventureAddBindingModel, AdventureServiceModel.class);

        adventureService.editById(id, adventureServiceModel);
        return redirect("/adventures/all");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        adventureService.deleteAdventureById(id);
        return redirect("/adventures/all");
    }
}
