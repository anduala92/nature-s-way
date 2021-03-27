package com.example.naturesway.web.controllers;

import com.example.naturesway.domain.binding.EventBindingModel;
import com.example.naturesway.domain.serviceModels.AdventureServiceModel;
import com.example.naturesway.domain.serviceModels.EventServiceModel;
import com.example.naturesway.domain.serviceModels.UserServiceModel;
import com.example.naturesway.domain.viewModels.AdventureViewModel;
import com.example.naturesway.domain.viewModels.EventViewModel;
import com.example.naturesway.service.EventService;
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
@RequestMapping("/events")
public class EventController extends BaseController{
    private final UserService userService;
    private final EventService eventService;
    private final ModelMapper mapper;

    public EventController(UserService userService, EventService eventService, ModelMapper mapper) {
        this.userService = userService;
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

        String username = getCurrentUser();
        for (EventViewModel event : events) {
            for (UserServiceModel user : event.getUsers()) {
                if (user.getUsername().equals(username)){
                    event.setFavorite(true);
                }
            }
        }
        modelAndView.addObject("adventures", events);
        return view("event/upcoming-events", modelAndView);
    }

    @GetMapping ("/upcoming-events/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView addToFavorites(@PathVariable String id){
        EventServiceModel eventServiceModel = eventService.findById(id);
        String username = getCurrentUser();
        UserServiceModel userServiceModel = userService.findUserByUsername(username);
        eventServiceModel.getUsers().add(userServiceModel);

        eventService.updateEvent(eventServiceModel);
        return redirect("/events/upcoming-events");
    }


    @GetMapping("/favorites")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Favorite Events")
    public ModelAndView favoritesAdventures(ModelAndView modelAndView) {
        String username = getCurrentUser();

        List<EventViewModel> events = eventService.findAll()
                .stream()
                .map(event -> mapper.map(event, EventViewModel.class))
                .collect(Collectors.toList());

        List<EventViewModel> favoriteEvents = new ArrayList<>();

        for (EventViewModel eventViewModel : events) {
            for (UserServiceModel eventUser : eventViewModel.getUsers()) {
                if (eventUser.getUsername().equals(username)){
                    favoriteEvents.add(eventViewModel);
                }
            }
        }
        modelAndView.addObject("events", favoriteEvents);

        return view("favorites/events", modelAndView);
    }

    @GetMapping ("/favorites/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView removeFromFavorites(@PathVariable String id){
        EventServiceModel eventServiceModel = eventService.findById(id);
        String username = getCurrentUser();

        Set<UserServiceModel> filteredUsers = new HashSet<>();
        for (UserServiceModel user : eventServiceModel.getUsers()) {
            if (!user.getUsername().equals(username)){
                filteredUsers.add(user);
            }
        }
        eventServiceModel.setUsers(filteredUsers);
        eventService.updateEvent(eventServiceModel);
        return redirect("/events/favorites");
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
