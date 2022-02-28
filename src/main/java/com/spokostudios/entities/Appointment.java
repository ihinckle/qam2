package com.spokostudios.entities;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * An appointment object
 * Includes holding an associated Contact, Customer, and User
 */
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

    /**
     * Gets the appointment ID
     * @return The appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the appointment title
     * @return The appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the appointment description
     * @return The appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the appointment location
     * @return The appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the appointment contact
     * @return The contact on the appointment
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Gets the appointment type
     * @return The appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the appointment start time in UTC
     * @return The start time in UTC
     */
    public ZonedDateTime getStartInUTC() {
        return startInUTC;
    }

    /**
     * Gets the start time as a TimeOption
     * @return The start time represented as a TimeOption
     */
    public TimeOption getStartTimeOption(){
        return new TimeOption(getStartInUTC().getHour());
    }

    /**
     * Gets the end time in UTC
     * @return The end time in UTC
     */
    public ZonedDateTime getEndInUTC() {
        return endInUTC;
    }

    /**
     * Gets the end time as a TimeOption
     * @return The end time represented as a TimeOption
     */
    public TimeOption getEndTimeOption(){
        return new TimeOption(getEndInUTC().getHour());
    }

    /**
     * Gets the customer on the appointment
     * @return The customer on the appointment
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the ID of the customer on the appointment
     * @return The ID of the customer on the appointment
     */
    public int getCustomerId() {
        return customer.getId();
    }

    /**
     * Gets the user on the appointment
     * @return The user on the appointment
     */
    public User getUser() {
        return user;
    }

    /**
     * Easy method for using a PropertyValueFactory
     * @return The user ID
     */
    public int getUserId(){
        return user.getId();
    }

    /**
     * Gets the date of the appointment
     * @return The date of the appointment
     */
    public LocalDate getDate(){
        return startInUTC.toLocalDate();
    }
}
