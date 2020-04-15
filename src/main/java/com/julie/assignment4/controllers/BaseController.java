package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.*;
import com.julie.assignment4.repository.CustomerRepository;
import com.julie.assignment4.sort2.SortStrategyFactory;
import com.julie.assignment4.sort2.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.*;

@Controller
public class BaseController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/createAccount")
    public String main() {
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String createAccount(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String password) {

        Customer.CustomerBuilder customerBuilder = new Customer.CustomerBuilder();
        Customer c = customerBuilder
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .build();

        customerRepository.save(c);

        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {

        Customer c = customerRepository.getByEmail(email);

        if (c != null && c.getPassword().equals(password)) {
            request.getSession().setAttribute("loggedCustomer", c);
            return "redirect:profile";
        } else {

            return "login";
        }
    }

    @GetMapping("/products")
    public String products(@PathParam("order") String order,
                           @PathParam("name") String name,
                           Model model) {

        Category c = new Category();
        c.setCategoryTitle("Bakery");

        Product p1 = new Product();
        p1.setProductID(1);
        p1.setCategories(Arrays.asList(c));
        p1.setManufacturer("Manufacturer1");
        p1.setPrice(1.0);
        p1.setProductTitle("B The Souper Flour");
        p1.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p1.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
                "The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
                "making it look like readable English.");

        Product p2 = new Product();
        p1.setProductID(2);
        p2.setCategories(Arrays.asList(c));
        p2.setManufacturer("Manufacturer2");
        p2.setPrice(2.0);
        p2.setProductTitle("C The Souper Flour");
        p2.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p2.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.");

        Product p3 = new Product();
        p1.setProductID(3);
        p3.setCategories(Arrays.asList(c));
        p3.setManufacturer("Manufacturer3");
        p3.setPrice(3.0);
        p3.setProductTitle("A The Souper Flour");
        p3.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p3.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.");

        Product p4 = new Product();
        p1.setProductID(4);
        p4.setCategories(Arrays.asList(c));
        p4.setManufacturer("Manufacturer4");
        p4.setPrice(4.0);
        p4.setProductTitle("E The Souper Flour");
        p4.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p4.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.");

        List<Product> products = Arrays.asList(p1, p2, p3, p4);

        if (name == null) {
            name = "";
        }

        if (order == null) {
            order = "asc";
        }

        Sorter sorter = new Sorter(products, SortStrategyFactory.getSortStrategy(name));
        model.addAttribute("products", sorter.sort(order.equals("asc")));

        return "products";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Category c = new Category();
        c.setCategoryTitle("Bakery");

        Product p1 = new Product();
        p1.setProductID(1);
        p1.setCategories(new ArrayList<>(Arrays.asList(c)));
        p1.setManufacturer("Manufacturer");
        p1.setPrice(2.0);
        p1.setProductTitle("The Souper Flour");
        p1.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p1.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
                "The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
                "making it look like readable English.");

        Product p2 = new Product();
        p1.setProductID(2);
        p2.setCategories(new ArrayList<>(Arrays.asList(c)));
        p2.setManufacturer("Manufacturer");
        p2.setPrice(2.0);
        p2.setProductTitle("The Souper Flour");
        p2.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p2.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.");

        Product p3 = new Product();
        p1.setProductID(3);
        p3.setCategories(new ArrayList<>(Arrays.asList(c)));
        p3.setManufacturer("Manufacturer");
        p3.setPrice(2.0);
        p3.setProductTitle("The Souper Flour");
        p3.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p3.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.");

        Map<Product, Integer> products = new HashMap<>();//Arrays.asList(p1, p2, p3);
        products.put(p1, 1);
        products.put(p2, 3);
        products.put(p3, 2);

        model.addAttribute("products", products);
        model.addAttribute("total", getCartTotal(products));
        return "cart";
    }

    // FIXME Move out from controller
    private double getCartTotal(Map<Product, Integer> products) {
        double result = 0.0;

        for (Map.Entry<Product, Integer> prod : products.entrySet()) {
            result += prod.getKey().getPrice() * prod.getValue();
        }

        return result;
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Model model) {
        Category c = new Category();
        c.setCategoryTitle("Bakery");

        Customer customer = new Customer();
        customer.setFirstName("Julie");

        Review r = new Review();
        r.setScore(3);
        r.setComment("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
        r.setCustomer(customer);

        Product p1 = new Product();
        p1.setProductID(1);
        p1.setCategories(Arrays.asList(c));
        p1.setManufacturer("Manufacturer");
        p1.setPrice(2.0);
        p1.setProductTitle("The Souper Flour");
        p1.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
        p1.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
                "The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
                "making it look like readable English.");

        model.addAttribute("product", p1);
        model.addAttribute("reviews", Arrays.asList(r));

        return "ViewProduct";
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
        Customer c = (Customer) request.getSession().getAttribute("loggedCustomer");

        if (builder == null) {
            builder = new Customer.CustomerBuilder(c);
            request.getSession().setAttribute("customerBuilder", builder);
        }

        switch (mode) {
            // FIXME In a method
            case "address":
                Address.AddressBuilder addressBuilder = new Address.AddressBuilder()
                        .setCity((String) allParams.get("city"))
                        .setCustomer(c)
                        .setLine1((String) allParams.get("line1"))
                        .setLine2((String) allParams.get("line2"))
                        .setCountry((String) allParams.get("country"));

                builder.addAddress(addressBuilder);

                break;
            case "details":
                break;
            case "loyaltycard":
                LoyaltyCard loyaltyCard = new LoyaltyCard();
                loyaltyCard.setLoyaltyCardID(Long.valueOf((String) allParams.get("loyaltyCardNo")));
                loyaltyCard.setCardType(LoyaltyCard.Type.valueOf(((String) allParams.get("loyaltyCardType")).toUpperCase()));
                builder.setLoyaltyCard(loyaltyCard);
                break;
        }

        model.addAttribute("customer", builder.build());

        return "profile";
    }

    @GetMapping("/viewUsers")
    public String viewCustomers() {
        return "viewUsers";
    }

    @GetMapping("/purchaseHistory")
    public String purchaseHistory() {
        return "purchaseHistory";
    }

    @GetMapping("/adminProducts")
    public String adminProducts() {
        return "adminProducts";
    }
}
