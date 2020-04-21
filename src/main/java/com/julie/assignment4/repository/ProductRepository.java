package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Category;
import com.julie.assignment4.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository  extends CrudRepository<Product, Long> {

    List<Product> findByCategoriesIn(List<Category> categories);

    List<Product> findByProductTitleContaining(String title);

    List<Product> findByManufacturerContaining(String manufacturer);

}
