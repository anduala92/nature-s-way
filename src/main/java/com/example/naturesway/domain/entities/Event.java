package com.example.naturesway.domain.entities;

import com.example.naturesway.constants.Constants;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event extends BaseEntity{
    private String name;
    private Date eventDate;
    private String location;
    private String programme;
    private Set<User> users;

    public Event() {
    }

    @Column(name = "name", unique = true, nullable = false)
    @Size(min = 3, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "event_date",  nullable = false)
    @Type(type="date")
    @DateTimeFormat(pattern = Constants.DATE_PATTERN)
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Column(name = "location", nullable = false)
    @Size(min = 3, max = 30)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "programme", nullable = false)
    @Size(min = 3)
    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    @ManyToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "events_users",
            joinColumns = @JoinColumn(
                    name = "event_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id",
                    referencedColumnName = "id"
            )
    )    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
