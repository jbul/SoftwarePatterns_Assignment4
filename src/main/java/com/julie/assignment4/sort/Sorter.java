package com.julie.assignment4.sort;

import com.julie.assignment4.entity.Product;
import com.julie.assignment4.sort.strategy.SortStrategy;

import java.util.Arrays;
import java.util.List;

public class Sorter {

    private SortStrategy strategy;
    private Product[] a;

    public Sorter(List<Product> list, SortStrategy strategy) {
        a = list.toArray(new Product[list.size()]);
        this.strategy = strategy;
    }

    public void setSortStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Product> sort(boolean ascending) {
        if (a.length > 0) {
            doSort(ascending);
        }
        return Arrays.asList(a);
    }

    private void doSort(boolean ascending){
        Product[] tempArray = new Product[a.length];
        mergeSort(tempArray,0,a.length-1, ascending);
    }

    private void mergeSort(Product[] tempArray,int lowerIndex,int upperIndex, boolean ascending){
        if(lowerIndex == upperIndex){
            return;
        }else{
            int mid = (lowerIndex+upperIndex)/2;
            mergeSort(tempArray, lowerIndex, mid, ascending);
            mergeSort(tempArray, mid+1, upperIndex, ascending);
            merge(tempArray,lowerIndex,mid+1,upperIndex, ascending);
        }
    }

    private void merge(Product[] tempArray,int lowerIndexCursor,int higerIndex,int upperIndex, boolean ascending){
        int tempIndex=0;
        int lowerIndex = lowerIndexCursor;
        int midIndex = higerIndex-1;
        int totalItems = upperIndex-lowerIndex+1;
        while(lowerIndex <= midIndex && higerIndex <= upperIndex ){

            if(strategy.isBefore(a[lowerIndex], a[higerIndex]) && ascending){
                tempArray[tempIndex++] = a[lowerIndex++];
            }else{
                tempArray[tempIndex++] = a[higerIndex++];
            }
        }

        while(lowerIndex <= midIndex){
            tempArray[tempIndex++] = a[lowerIndex++];
        }

        while(higerIndex <= upperIndex){
            tempArray[tempIndex++] = a[higerIndex++];
        }

        for(int i=0;i<totalItems;i++){
            a[lowerIndexCursor+i] = tempArray[i];
        }
    }




}
