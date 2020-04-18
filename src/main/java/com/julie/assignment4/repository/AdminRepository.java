package com.julie.assignment4.repository;

import com.julie.assignment4.entity.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    public Admin getByEmail(String email);
}
