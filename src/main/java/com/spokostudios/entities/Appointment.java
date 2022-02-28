package com.spokostudios.entities;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private Contact contact;
    private String type;
    private ZonedDateTime startInUTC;
    private ZonedDateTime endInUTC;
    private Customer customer;
    private User user;

    public Appointment(int id,
                       String title,
                       String description,
                       String location,
                       Contact contact,
                       String type,
                       ZonedDateTime start,
                       ZonedDateTime end,
                       Customer customer,
                       User user){
        this(title,description,location,contact,type,start,end,customer,user);
        this.id = id;
    }

    public Appointment(String title,
                       String description,
                       String location,
                       Contact contact,
                       String type,
                       ZonedDateTime start,
                       ZonedDateTime end,
                       Customer customer,
                       User user){
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startInUTC = start;
        this.endInUTC = end;
        this.customer = customer;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Contact getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStartInUTC() {
        return startInUTC;
    }

    public TimeOption getStartTimeOption(){
        return new TimeOption(getStartInUTC().getHour());
    }

    public ZonedDateTime getEndInUTC() {
        return endInUTC;
    }

    public TimeOption getEndTimeOption(){
        return new TimeOption(getEndInUTC().getHour());
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomerId() {
        return customer.getId();
    }

    public User getUser() {
        return user;
    }

    public int getUserId(){
        return user.getId();
    }

    public LocalDate getDate(){
        return startInUTC.toLocalDate();
    }
}
