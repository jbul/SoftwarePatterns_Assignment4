package com.julie.assignment4.observer;

import com.julie.assignment4.entity.Customer;
import com.julie.assignment4.entity.UpdateMessage;

import java.util.Date;

public class ProductObserver implements Observer {

    private Customer customer;

    public ProductObserver(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void update(String message) {
        customer.getMessages().add(new UpdateMessage(new Date(), message, customer));
    }
}
