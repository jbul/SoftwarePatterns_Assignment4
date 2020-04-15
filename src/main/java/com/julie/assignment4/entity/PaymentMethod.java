package com.julie.assignment4.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentID;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    public PaymentMethod(){}

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
