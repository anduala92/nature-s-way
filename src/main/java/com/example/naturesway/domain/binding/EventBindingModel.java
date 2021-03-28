package com.example.naturesway.domain.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.example.naturesway.constants.Constants.*;


public class EventBindingModel {
    private String id;
    private String name;
    private Date eventDate;
    private String location;
    private String programme;

    public EventBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = NULL_EVENT_NAME_MESSAGE)
    @NotEmpty(message = EMPTY_EVENT_NAME_MESSAGE)
    @Length(min = 3, max = 30, message = INVALID_EVENT_NAME_MESSAGE)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = NULL_EVENT_DATE_MESSAGE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    @FutureOrPresent(message = FUTURE_OR_PRESENT_EVENT_DATE_MESSAGE)
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @NotNull(message = NULL_EVENT_LOCATION_MESSAGE)
    @NotEmpty(message = EMPTY_EVENT_LOCATION_MESSAGE)
    @Length(min = 3, max = 30, message = INVALID_EVENT_LOCATION_MESSAGE)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NotNull(message = NULL_EVENT_PROGRAMME_MESSAGE)
    @NotEmpty(message = EMPTY_EVENT_PROGRAMME_MESSAGE)
    @Length(min = 3, message = INVALID_EVENT_PROGRAMME_MESSAGE)
    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
