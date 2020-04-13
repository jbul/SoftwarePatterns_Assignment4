package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Customer getByEmail(String email);

}
