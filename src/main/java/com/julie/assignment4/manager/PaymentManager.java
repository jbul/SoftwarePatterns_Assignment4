package com.julie.assignment4.manager;

import com.julie.assignment4.entity.PaymentMethod;

public class PaymentManager {

    private PaymentMethod paymentMethod;

    public PaymentManager(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    public boolean doPayment() {
        return true;
    }
}
