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
}
