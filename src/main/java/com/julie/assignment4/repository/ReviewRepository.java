package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Product;
import com.julie.assignment4.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    public List<Review> findByProduct(Product product);
}
