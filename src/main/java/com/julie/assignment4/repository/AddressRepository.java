package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository  extends CrudRepository<Address, Long> {
}
