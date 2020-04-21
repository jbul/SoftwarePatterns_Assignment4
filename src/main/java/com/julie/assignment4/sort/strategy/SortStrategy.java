package com.julie.assignment4.sort.strategy;

import com.julie.assignment4.entity.Product;

public interface SortStrategy {

    public boolean isBefore(Product one, Product two);
}
