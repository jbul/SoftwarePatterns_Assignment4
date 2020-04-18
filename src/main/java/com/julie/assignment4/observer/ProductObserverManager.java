package com.julie.assignment4.observer;

import com.julie.assignment4.entity.Customer;
import com.julie.assignment4.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductObserverManager {

    private List<Product> observedProducts;

    private static ProductObserverManager manager;

    private ProductObserverManager() {
        this.observedProducts = new ArrayList<>();
    }


    public static ProductObserverManager getInstance() {
        if (manager == null) {
            manager = new ProductObserverManager();
        }
        return manager;
    }

    public void addObserver(Product p, Customer c) {

        if (!observedProducts.contains(p)) {
            observedProducts.add(p);
        }
        p.addObserver(new ProductObserver(c));
    }

    public void removeObserver(Product p, Customer c) {

        if (!observedProducts.contains(p)) {

//            for (Observer o : p.)
        }

    }

    public void productChanged(Product p) {
        if (observedProducts.contains(p)) {
            observedProducts.get( observedProducts.indexOf(p)).notifyObservers();
        }
    }
}
