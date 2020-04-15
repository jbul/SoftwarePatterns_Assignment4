package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository  extends CrudRepository<Category, Long> {
}
