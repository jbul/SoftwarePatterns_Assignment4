package com.julie.assignment4.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;
    @OneToOne(cascade = CascadeType.ALL)
    private LoyaltyCard loyaltyCard;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;
    @Transient
    private List<UpdateMessage> messages;

    public Customer(){
        customerOrders = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        addresses = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public Customer(String lastName, String firstName, String email, String password, List<PaymentMethod> paymentMethods, LoyaltyCard loyaltyCard, List<Address> addresses) {
        super(lastName, firstName, email, password);
        this.paymentMethods = new ArrayList<>();
        this.paymentMethods = paymentMethods;
        this.loyaltyCard = loyaltyCard;
        this.addresses = addresses;
        messages = new ArrayList<>();
    }


    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


    public List<UpdateMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UpdateMessage> messages) {
        this.messages = messages;
    }

    public static class CustomerBuilder{

        private long userID;
        private String lastName;
        private String firstName;
        private String email;
        private String password;
        private String shippingAddress;
        private List<PaymentMethod> paymentMethods;
        private List<CustomerOrder> customerOrders;
        private LoyaltyCard loyaltyCard;
        private List<Address.AddressBuilder> addressBuilderList;

        public CustomerBuilder() {
            addressBuilderList = new ArrayList<>();
            paymentMethods = new ArrayList<>();
        }

        public CustomerBuilder(Customer customer) {
            this();
            userID = customer.getUserID();
            lastName = customer.getLastName();
            firstName = customer.getFirstName();
            email = customer.getEmail();
            password = customer.getPassword();

            for (Address address : customer.getAddresses()) {

                addressBuilderList.add(new Address.AddressBuilder(address));
            }

            loyaltyCard = customer.getLoyaltyCard();
            paymentMethods = customer.getPaymentMethods();
        }

        public CustomerBuilder setUserID(long userID){
            this.userID = userID;
            return this;
        }

        public CustomerBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        public CustomerBuilder setPassword(String password){
            this.password = password;
            return this;
        }


        public CustomerBuilder setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public CustomerBuilder setPaymentMethods(List<PaymentMethod> paymentMethods) {
            this.paymentMethods = paymentMethods;
            return this;
        }

        public CustomerBuilder addPaymentMethod(PaymentMethod method) {
            this.paymentMethods.add(method);
            return this;
        }

        public CustomerBuilder setCustomerOrders(List<CustomerOrder> customerOrders) {
            this.customerOrders = customerOrders;
            return this;
        }

        public CustomerBuilder setLoyaltyCard(LoyaltyCard loyaltyCard) {
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
            customer.setPaymentMethods(this.paymentMethods);
            customer.setCustomerOrders(this.customerOrders);
            customer.setLoyaltyCard(this.loyaltyCard);
            if(addressBuilderList != null) {
                for (Address.AddressBuilder a : addressBuilderList) {
                    customer.getAddresses().add(a.build());
                }
            }

            return customer;
        }

        public CustomerBuilder addAddress(Address.AddressBuilder addressBuilder){
            addressBuilderList.add(addressBuilder);
            return this;

        }
    }
}
