package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.*;
import com.julie.assignment4.repository.CustomerRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/createAccount")
    public String createAccount(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String password) {

        Customer.CustomerBuilder customerBuilder = new Customer.CustomerBuilder();
        Customer customer = customerBuilder
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .build();

        customerRepository.save(customer);

        return "login";
    }

    @Transactional
    @PostMapping("/login")
    public String userLogin(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {

        Customer customer = customerRepository.getByEmail(email);


        if (customer != null && customer.getPassword().equals(password)) {

            Hibernate.initialize(customer.getPaymentMethods());
            Hibernate.initialize(customer.getCustomerOrders());
            Hibernate.initialize(customer.getReviews());

            request.getSession().setAttribute("loggedCustomer", customer);
            return "redirect:products";

        } else {
            return "login";
        }
    }

    @GetMapping("/profile")
    public String viewProfile(@PathParam("userId") String userId, Model model, HttpServletRequest request) {
        Customer loggedCustomer = (Customer) request.getSession().getAttribute("loggedCustomer");
        if (loggedCustomer != null) {
            model.addAttribute("customer", loggedCustomer);
        } else if (userId != null) {
            Customer c = customerRepository.findById(Long.valueOf(userId)).get();
            model.addAttribute("customer", c);
        } else {
            return "login";
        }
        return "Profile";
    }

    @PostMapping("/updateCustomer/{mode}")
    public String updateCustomer(@PathVariable("mode") String mode,
                                 @RequestParam Map<String, Object> allParams,
                                 HttpServletRequest request,
                                 Model model) {

        Customer.CustomerBuilder builder = (Customer.CustomerBuilder) request.getSession().getAttribute("customerBuilder");
        Customer loggedCustomer = (Customer) request.getSession().getAttribute("loggedCustomer");

        if (builder == null) {
            builder = new Customer.CustomerBuilder(loggedCustomer);
            request.getSession().setAttribute("customerBuilder", builder);
        }

        switch (mode) {
            case "address":
                addCustomerAddress(allParams, builder, loggedCustomer);

                break;
            case "details":
                break;
            case "loyaltycard":
                createCustomerLoyaltyCard(allParams, builder);
                break;
            case "creditcard":
                addCreditCard(allParams, builder);
                break;
            case "paypal":
                addPaypal(allParams, builder);
            case "save":
                customerRepository.save(builder.build());
                break;
        }

        model.addAttribute("customer", builder.build());

        return "profile";
    }

    private void createCustomerLoyaltyCard(@RequestParam Map<String, Object> allParams, Customer.CustomerBuilder builder) {
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setLoyaltyCardID(Long.valueOf((String) allParams.get("loyaltyCardNo")));
        loyaltyCard.setCardType(LoyaltyCard.Type.valueOf(((String) allParams.get("loyaltyCardType")).toUpperCase()));
        builder.setLoyaltyCard(loyaltyCard);
    }

    private void addCustomerAddress(@RequestParam Map<String, Object> allParams, Customer.CustomerBuilder builder, Customer c) {
        Address.AddressBuilder addressBuilder = new Address.AddressBuilder()
                .setCity((String) allParams.get("city"))
                .setCustomer(c)
                .setLine1((String) allParams.get("line1"))
                .setLine2((String) allParams.get("line2"))
                .setCountry((String) allParams.get("country"));

        builder.addAddress(addressBuilder);
    }

    private void addCreditCard(@RequestParam Map<String, Object> allParams, Customer.CustomerBuilder builder) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber((String) allParams.get("cardNo"));
        builder.addPaymentMethod(creditCard);
    }

    private void addPaypal(@RequestParam Map<String, Object> allParams, Customer.CustomerBuilder builder) {
        Paypal paypal = new Paypal();
        paypal.setEmailAccount((String) allParams.get("paypalEmail"));
        builder.addPaymentMethod(paypal);
    }


}
