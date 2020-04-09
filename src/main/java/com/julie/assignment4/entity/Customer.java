package com.julie.assignment4.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Customer extends User {

    private String shippingAddress;

    @OneToMany
    private PaymentMethod paymentMethod;

    @OneToMany
    private List<Order> orders;

    @OneToOne
    private LoyaltyCard loyaltyCard;

    public Customer(){

    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
}
