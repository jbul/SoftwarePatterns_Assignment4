package com.julie.assignment4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    @PostMapping("/pay")
    public String pay() {

        return "paymentSuccess";
    }

}
