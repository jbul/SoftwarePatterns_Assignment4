package com.julie.assignment4.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class CreditCard extends PaymentMethod {


    public CreditCard(){}

    @Override
    public boolean checkAuthorization() {
        System.out.println("Bank issued authorization...");
        return true;
    }

    @Override
    public boolean pay() {
        System.out.println("Card issued payment");
        return true;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("Payment validated");
        return true;
    }

}
