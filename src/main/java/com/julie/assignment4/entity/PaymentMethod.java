package com.julie.assignment4.entity;

import javax.persistence.*;

@Entity
public abstract class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentID;

    public PaymentMethod(){}

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public final void makePayment(){
        checkAuthorization();
        pay();
        validatePayment();

    }

    public abstract boolean checkAuthorization();
    public abstract boolean pay();
    public abstract boolean validatePayment();


}
