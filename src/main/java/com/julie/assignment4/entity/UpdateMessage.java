package com.julie.assignment4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class UpdateMessage {

    Long id;
    Date date;
    String message;

    @JsonIgnore
    Customer customer;

    public UpdateMessage(Date date, String message, Customer customer) {
        this.date = date;
        this.message = message;
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
