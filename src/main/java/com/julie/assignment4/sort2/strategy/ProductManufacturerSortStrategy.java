package com.julie.assignment4.sort2.strategy;

import com.julie.assignment4.entity.Product;

public class ProductManufacturerSortStrategy implements SortStrategy {
    @Override
    public boolean isBefore(Product one, Product two) {
        return one.getManufacturer().compareTo(two.getManufacturer()) < 0;
    }
}
