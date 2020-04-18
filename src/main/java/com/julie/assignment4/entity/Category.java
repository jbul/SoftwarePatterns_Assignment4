package com.julie.assignment4.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryID;
    private String categoryTitle;
    private String description;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    public Category(){}

    public Category(String categoryTitle, String description) {
        this.categoryTitle = categoryTitle;
        this.description = description;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int compareTo(Category o) {
        return this.categoryTitle.compareTo(o.categoryTitle);
    }
}
