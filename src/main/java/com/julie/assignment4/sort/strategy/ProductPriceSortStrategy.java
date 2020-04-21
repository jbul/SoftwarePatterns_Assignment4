package com.julie.assignment4.sort.strategy;

import com.julie.assignment4.entity.Product;

public class ProductPriceSortStrategy implements SortStrategy {

    @Override
    public boolean isBefore(Product one, Product two) {
        return one.getPrice() < two.getPrice();
    }
}
