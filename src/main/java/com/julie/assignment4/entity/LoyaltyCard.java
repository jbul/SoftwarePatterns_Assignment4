package com.julie.assignment4.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoyaltyCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loyaltyCardID;
    private Date cardPurchase;
    private Type cardType;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    public LoyaltyCard(){}

    public LoyaltyCard(Date cardPurchase, Type cardType) {
        this.cardPurchase = cardPurchase;
        this.cardType = cardType;
    }

    public long getLoyaltyCardID() {
        return loyaltyCardID;
    }

    public void setLoyaltyCardID(long loyaltyCardID) {
        this.loyaltyCardID = loyaltyCardID;
    }

    public Date getCardPurchase() {
        return cardPurchase;
    }

    public void setCardPurchase(Date cardPurchase) {
        this.cardPurchase = cardPurchase;
    }

    public Type getCardType() {
        return cardType;
    }

    public void setCardType(Type cardType) {
        this.cardType = cardType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public enum Type{
        GOLD, SILVER, BRONZE;
    }



}
