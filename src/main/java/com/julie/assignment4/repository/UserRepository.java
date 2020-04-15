package com.julie.assignment4.repository;

import com.julie.assignment4.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
