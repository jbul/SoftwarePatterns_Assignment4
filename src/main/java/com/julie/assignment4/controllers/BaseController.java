package com.julie.assignment4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/createAccount")
    public String main(){
        return "createAccount";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/products")
    public String products(){
        return "products";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(){
        return "ViewProduct";
    }

    @GetMapping("/profile")
    public String viewProfile(){
        return "Profile";
    }
}
