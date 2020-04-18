package com.julie.assignment4.entity;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String line1;
    private String line2;
    private String city;
    private String country;
    private boolean isMainAddress;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    public Address(){}

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isMainAddress() {
        return isMainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        isMainAddress = mainAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static class AddressBuilder{
        private String line1;
        private String line2;
        private String city;
        private String country;
        private boolean isMainAddress;
        private Customer customer;

        public AddressBuilder setLine1(String line1) {
            this.line1 = line1;
            return this;
        }

        public AddressBuilder setLine2(String line2) {
            this.line2 = line2;
            return this;
        }


        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }


        public AddressBuilder setMainAddress(boolean mainAddress) {
            isMainAddress = mainAddress;
            return this;
        }

        public AddressBuilder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Address build(){

            Address address = new Address();
            address.setLine1(this.line1);
            address.setLine2(this.line2);
            address.setCity(this.city);
            address.setCountry(this.country);
            address.setMainAddress(this.isMainAddress);
            address.setCustomer(this.customer);

            return address;
        }
    }
}
