package com.julie.assignment4.entity;

import javax.persistence.*;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderLineID;
    private int quantity;
    @OneToOne
    private Product product;

    public OrderLine(){}

    public long getOrderLineID() {
        return orderLineID;
    }

    public void setOrderLineID(long orderLineID) {
        this.orderLineID = orderLineID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
