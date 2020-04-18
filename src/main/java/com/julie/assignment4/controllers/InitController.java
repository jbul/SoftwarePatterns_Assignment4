package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.*;
import com.julie.assignment4.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Date;

@Controller
public class InitController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    LoyaltyCardRepository loyaltyCardRepository;

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/config/init")
    public ResponseEntity<String> init() {

        // Create 2 reviews for each product
        Review reviewEatReal = new Review();

        // Create 3 categories, 1 category is same for the 2 products
        Category categoryOrganic = new Category("Organic", "Organic food is food produced by methods that comply with the standards of organic farming." );
        Category categoryFood = new Category("Food",  "All our wonderful food products that will be delightful for you.");
        Category categoryVegan = new Category("Vegan", "Products not from animal origin, still very good though." );

        categoryRepository.saveAll(Arrays.asList(categoryOrganic, categoryFood, categoryVegan));

        // Create 2 products
        Product tea = new Product("After Dinner", "Pukka", 2.50,
                "https://images.hollandandbarrettimages.co.uk/productimages/HB/370/076723_A.jpg",
                "After Dinner is a wonderfully rich and naturally caffeine-free herbal tea.",
                Arrays.asList(categoryOrganic, categoryVegan));

        Product hummusCrisps = new Product("Sea Salt Hummus Chips", "Eat Real", 1.75,
                "https://images.hollandandbarrettimages.co.uk/productimages/HB/370/032946_A.jpg",
                "These sea salted hummus chips are an healthy alternative to standard snacks, ideal for those who wish to avoid Gluten and Lactose but still wish to share with friends and family.",
                Arrays.asList(categoryFood, categoryVegan));

        productRepository.saveAll(Arrays.asList(tea, hummusCrisps));

        // create 1 orderline per product
        OrderLine orderLine1 = new OrderLine(2, tea);
        OrderLine orderLine2 = new OrderLine(5, hummusCrisps);

        orderLineRepository.saveAll(Arrays.asList(orderLine1, orderLine2));


        // Create 1 order for all order lines
        CustomerOrder order = new CustomerOrder();
        order.setOrderLineList(Arrays.asList(orderLine1, orderLine2));
        order.setTotalPrice(orderLine1.getProduct().getPrice() * orderLine1.getQuantity() + orderLine2.getProduct().getPrice() * orderLine2.getQuantity());

        customerOrderRepository.save(order);

        //Create PaymentMethod
        PaymentMethod paypalPayment = new Paypal("juliebulanda.jb@gmail.com");
        paymentMethodRepository.save(paypalPayment);

        PaymentMethod card = new CreditCard("123455667");
        paymentMethodRepository.save(card);

        // Create Loyaltycard for user
        LoyaltyCard loyaltyCard = new LoyaltyCard(new Date(), LoyaltyCard.Type.SILVER);
        loyaltyCardRepository.save(loyaltyCard);

        // Create a customer, add order to customer
        Customer customer = new Customer.CustomerBuilder().setEmail("juliebulandajb@gmail.com")
                .setCustomerOrders(Arrays.asList(order))
                .setFirstName("Julie")
                .setLastName("Bulanda")
                .setPassword("123456")
                .setPaymentMethods(Arrays.asList(paypalPayment, card))
                .setLoyaltyCard(loyaltyCard)
                .build();

        // Persist using CustomerRepository
        customerRepository.save(customer);

        //Set address for customer
        Address primaryAddress = new Address.AddressBuilder().setLine1("12 The Richmond")
                .setLine2("Arran Quay")
                .setCity("Dublin")
                .setCountry("Ireland")
                .setMainAddress(true)
                .setCustomer(customer)
                .build();

        addressRepository.save(primaryAddress);

        //Create 1 admin
        User admin = new Admin("Bloggs", "Joe", "jbloggs@gmail.com", "123456");

        userRepository.save(admin);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
