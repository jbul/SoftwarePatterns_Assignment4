package com.julie.assignment4.sort.strategy;

import com.julie.assignment4.entity.Product;

public class ProductCategorySortStrategy implements SortStrategy {

    @Override
    public boolean isBefore(Product one, Product two) {
        return one.getCategories().get(0).getCategoryTitle().compareTo(two.getCategories().get(0).getCategoryTitle()) < 0;
    }
}
