package com.julie.assignment4.entity;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(String lastName, String firstName, String email, String password) {
        super(lastName, firstName, email, password);
    }
}
