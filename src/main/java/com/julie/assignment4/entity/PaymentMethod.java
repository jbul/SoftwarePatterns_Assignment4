package com.julie.assignment4.entity;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentID;

    @ManyToOne(cascade = CascadeType.ALL)
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

    public final void makePayment(Double amount){
        System.out.println("Paying: " + amount);
        checkAuthorization(amount);
        pay(amount);
        validatePayment();
    }

    protected abstract boolean checkAuthorization(Double amount);
    protected abstract boolean pay(Double amount);
    protected abstract boolean validatePayment();

    public String toString() {
        return getClass().getSimpleName();
    }
}
