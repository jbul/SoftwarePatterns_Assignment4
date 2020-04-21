package com.julie.assignment4.entity;

import javax.persistence.Entity;

@Entity
public class CreditCard extends PaymentMethod {

    private String cardNumber;

    public CreditCard() {

    }

    public CreditCard(String number){
        cardNumber = number;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean checkAuthorization(Double amount) {
        System.out.println("Bank issued authorization...");
        return true;
    }

    @Override
    public boolean pay(Double amount) {
        System.out.println("Card " +  cardNumber +  " issued payment of " + amount + " euro");
        return true;
    }

    @Override
    public boolean validatePayment() {
        System.out.println("PaymentStrategy validated");
        return true;
    }

}
