package com.julie.assignment4.sort;

import com.julie.assignment4.sort.strategy.*;

public class SortStrategyFactory {

    public static SortStrategy getSortStrategy(String name) {
        switch (name) {
            case "title":
                return new ProductTitleSortStrategy();
            case "category":
                return new ProductCategorySortStrategy();
            case "price":
                return new ProductPriceSortStrategy();
            case "manufacturer":
                return new ProductManufacturerSortStrategy();
            default:
                return new ProductTitleSortStrategy();
        }
    }
}
