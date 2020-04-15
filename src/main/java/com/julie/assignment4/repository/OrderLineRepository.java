package com.julie.assignment4.repository;

import com.julie.assignment4.entity.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLineRepository  extends CrudRepository<OrderLine, Long> {
}
