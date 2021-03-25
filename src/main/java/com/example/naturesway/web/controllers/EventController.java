package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.EventBindingModel;
import com.example.naturesway.domain.binding.LivingTipAddBindingModel;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.domain.serviceModels.LivingTipServiceModel;
import com.example.naturesway.domain.viewModels.EventViewModel;
import com.example.naturesway.domain.viewModels.LivingTipViewModel;
import com.example.naturesway.service.EventService;
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
@RequestMapping("/events")
public class EventController extends BaseController{
    private final EventService eventService;
    private final ModelMapper mapper;

    public EventController(EventService eventService, ModelMapper mapper) {
        this.eventService = eventService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Add Event")
    public ModelAndView addEvent(@ModelAttribute(name = "model") EventBindingModel model) {
        return view("event/add-event");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addEventConform(@Valid @ModelAttribute(name = "model") EventBindingModel model,
                                            BindingResult bindingResult) throws IOException, IllegalAccessException {
        if (bindingResult.hasErrors()){
            return view("/event/add-event");
        }
        EventServiceModel eventServiceModel = mapper.map(model, EventServiceModel.class);

        eventService.addEvent(eventServiceModel);

        return redirect("/events/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("All Events")
    public ModelAndView allEvents(ModelAndView modelAndView) {
        List<EventViewModel> events = eventService.findAll()
                .stream()
                .map(event -> mapper.map(event, EventViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("events", events);

        return view("event/all-event", modelAndView);
    }

    @GetMapping("/upcoming-events")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Upcoming Events")
    public ModelAndView upcomingEvents(ModelAndView modelAndView) {
        List<EventViewModel> events = eventService.findAll()
                .stream()
                .map(event -> mapper.map(event, EventViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("events", events);

        return view("event/upcoming-events", modelAndView);
    }

    @GetMapping ("/upcoming-events/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addToFavorites(@PathVariable String id){
        EventServiceModel eventServiceModel = eventService.findById(id);

        eventServiceModel.setFavorite(true);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        eventServiceModel.setUsername(username);

        eventService.updateEvent(eventServiceModel);
        return redirect("/events/upcoming-events");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Event")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView){
        EventServiceModel eventServiceModel = eventService.findById(id);

        modelAndView.addObject("event", mapper.map(eventServiceModel,EventBindingModel.class));

        return view("event/edit-event", modelAndView);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @Valid @ModelAttribute(name = "event") EventBindingModel eventBindingModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView){
        if (bindingResult.hasErrors()){
            return view("event/edit-event", modelAndView);
        }
        EventServiceModel eventServiceModel = mapper.map(eventBindingModel, EventServiceModel.class);
        eventService.editById(id, eventServiceModel);
        return redirect("/events/all");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@PathVariable String id){
        eventService.deleteEventById(id);
        return redirect("/events/all");
    }
}
