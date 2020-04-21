package com.julie.assignment4.sort.strategy;

import com.julie.assignment4.entity.Product;

public class ProductTitleSortStrategy implements SortStrategy {

    @Override
    public boolean isBefore(Product one, Product two) {
        return one.getProductTitle().compareTo(two.getProductTitle()) < 0;
    }
}
