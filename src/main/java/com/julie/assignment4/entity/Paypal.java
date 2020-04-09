package com.julie.assignment4.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Paypal extends PaymentMethod {

    public Paypal(){}

    @Override
    public boolean checkAuthorization() {
        System.out.println("Paypal authorization...");
        return true;
    }

    @Override
    public boolean pay() {
        System.out.println("Paypal issued payment");
        return true;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Payment validated");
        return true;
    }
}
