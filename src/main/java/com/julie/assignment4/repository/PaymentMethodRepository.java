package com.julie.assignment4.repository;

import com.julie.assignment4.entity.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodRepository  extends CrudRepository<PaymentMethod, Long> {
}
