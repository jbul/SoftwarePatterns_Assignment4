package com.julie.assignment4.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Paypal extends PaymentMethod {

    private String emailAccount;

    public Paypal(){}

    public String getEmailAccount() {
        return emailAccount;
    }

    public Paypal(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

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
        System.out.println("PaymentStrategy validated");
        return true;
    }
}
