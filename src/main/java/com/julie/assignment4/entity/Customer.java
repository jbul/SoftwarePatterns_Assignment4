package com.julie.assignment4.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Arrays;
import java.util.List;

@Entity
public class Customer extends User {

    private String shippingAddress;

    @OneToMany
    private List<PaymentMethod> paymentMethods;

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

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
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



    public static class Builder{

        private long userID;
        private String lastName;
        private String firstName;
        private String email;
        private String password;
        private String shippingAddress;
        private List<PaymentMethod> paymentMethods;
        private List<Order> orders;
        private LoyaltyCard loyaltyCard;


        public Builder setUserID(long userID){
            this.userID = userID;
            return this;
        }

        public Builder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Builder setPassword(String password){
            this.password = password;
            return this;
        }


        public Builder setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder setPaymentMethods(List<PaymentMethod> paymentMethods) {
            this.paymentMethods = paymentMethods;
            return this;
        }

        public Builder setOrders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Builder setLoyaltyCard(LoyaltyCard loyaltyCard) {
            this.loyaltyCard = loyaltyCard;
            return this;
        }

        public Customer build(){
            Customer customer = new Customer();

            customer.setUserID(this.userID);
            customer.setFirstName(this.firstName);
            customer.setLastName(this.lastName);
            customer.setEmail(this.email);
            customer.setPassword(this.password);
            customer.setShippingAddress(this.shippingAddress);
            customer.setPaymentMethods(this.paymentMethods);
            customer.setOrders(this.orders);
            customer.setLoyaltyCard(this.loyaltyCard);

            return customer;
        }
    }
}
