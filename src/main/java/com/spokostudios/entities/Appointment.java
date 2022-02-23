package com.spokostudios.entities;

import java.time.ZonedDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private int customerId;
    private int userId;

    public Appointment(int id,
                       String title,
                       String description,
                       String location,
                       String contact,
                       String type,
                       ZonedDateTime start,
                       ZonedDateTime end,
                       int customerId,
                       int userId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
    }
}
