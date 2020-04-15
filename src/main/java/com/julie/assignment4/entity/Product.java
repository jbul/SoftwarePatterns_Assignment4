package com.julie.assignment4.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productID;
    private String productTitle;
    private String manufacturer;
    private double price;
    private String productImage; //Associated image
    private String description;

    @OneToOne(cascade = CascadeType.MERGE)
    private OrderLine orderLine;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Review> reviews;


    public Product(){}

    public Product(String productTitle, String manufacturer, double price, String productImage, String description, List<Category> categories) {
        this.productTitle = productTitle;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productImage = productImage;
        this.description = description;
        this.categories = categories;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Product o) {

        if (this.productID > o.productID) {
            return 1;
        } else if (this.productID < o.productID) {
            return -1;
        }
        return 0;
    }
}
