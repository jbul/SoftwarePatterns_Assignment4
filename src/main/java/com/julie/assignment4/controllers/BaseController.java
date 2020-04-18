package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.*;
import com.julie.assignment4.observer.ProductObserverManager;
import com.julie.assignment4.repository.AdminRepository;
import com.julie.assignment4.repository.CustomerOrderRepository;
import com.julie.assignment4.repository.CustomerRepository;
import com.julie.assignment4.repository.ProductRepository;
import com.julie.assignment4.sort2.SortStrategyFactory;
import com.julie.assignment4.sort2.Sorter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.*;

@Controller
public class BaseController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CustomerOrderRepository customerOrderRepository;

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

    @Transactional
    @PostMapping("/login")
    public String userLogin(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {

        Customer c = customerRepository.getByEmail(email);

//        Admin a =adminRepository.getByEmail(email);

        if (c != null && c.getPassword().equals(password)) {

            Hibernate.initialize(c.getPaymentMethods());
            Hibernate.initialize(c.getCustomerOrders());
            Hibernate.initialize(c.getReviews());

            request.getSession().setAttribute("loggedCustomer", c);
            return "redirect:profile";
//        } else if (a != null && a.getPassword().equals(password)) {
//
//            request.getSession().setAttribute("loggedUser", a);
//            return "redirect:adminProducts";
        }else {
            return "login";
        }
    }

    @GetMapping("/products")
    public String products(@PathParam("order") String order,
                           @PathParam("name") String name,
                           Model model) {

        if (name == null) {
            name = "";
        }

        if (order == null) {
            order = "asc";
        }

        List<Product> products = new ArrayList<>();

        for (Product p : productRepository.findAll()) {
            products.add(p);
        }

        Sorter sorter = new Sorter(products, SortStrategyFactory.getSortStrategy(name));
        model.addAttribute("products", sorter.sort(order.equals("asc")));

        return "products";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute("loggedCustomer") != null) {
            model.addAttribute("customer", (Customer)request.getSession().getAttribute("loggedCustomer"));
        }

        Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        model.addAttribute("products", cart);
        model.addAttribute("total", getCartTotal(cart));
        return "cart";
    }

    @PostMapping("/addToCart")
    @Transactional
    public String addToCart(@RequestParam String quantity, @RequestParam String productID, HttpServletRequest httpServletRequest) {

        Map<Product, Integer> cart = (Map<Product, Integer>) httpServletRequest.getSession().getAttribute("cart");

        if (cart != null) {

            for (Map.Entry<Product, Integer> cartEntry : cart.entrySet()) {

                if (cartEntry.getKey().getProductID() == Integer.parseInt(productID)) {
                    cart.put(cartEntry.getKey(), cartEntry.getValue() + Integer.parseInt(quantity));
                } else {
                    Product p = productRepository.findById(Long.valueOf(productID)).get();

                    Hibernate.initialize(p.getCategories());

                    cart.put(p, Integer.parseInt(quantity));
                }
            }
        } else {
            cart=new HashMap<>();
            Product p = productRepository.findById(Long.valueOf(productID)).get();

            Hibernate.initialize(p.getCategories());

            cart.put(p, Integer.parseInt(quantity));
        }

        httpServletRequest.getSession().setAttribute("cart", cart);
        return "redirect:viewProduct?id=" + productID;
    }

    // FIXME Move out from controller
    private double getCartTotal(Map<Product, Integer> products) {
        double result = 0.0;

        if (products != null) {
            for (Map.Entry<Product, Integer> prod : products.entrySet()) {
                result += prod.getKey().getPrice() * prod.getValue();
            }
        }
        return result;
    }

    @GetMapping("/viewProduct")
    @Transactional
    public String viewProduct(@RequestParam("id") String productId, Model model) {


        Product p = productRepository.findById(Long.valueOf(productId)).get();

//        Category c = new Category();
//        c.setCategoryTitle("Bakery");

        Hibernate.initialize(p.getCategories());
        Hibernate.initialize(p.getOrderLine());

        Customer customer = new Customer();
        customer.setFirstName("Julie");

        Review r = new Review();
        r.setScore(3);
        r.setComment("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
        r.setCustomer(customer);

//        Product p1 = new Product();
//        p1.setProductID(1);
//        p1.setCategories(Arrays.asList(c));
//        p1.setManufacturer("Manufacturer");
//        p1.setPrice(2.0);
//        p1.setProductTitle("The Souper Flour");
//        p1.setProductImage("https://i5.walmartimages.com/asr/15232da7-ee4e-44f5-b161-19108b8eb291_1.cc1421268d79193b6aa57566636ae1c1.jpeg?odnWidth=450&odnHeight=450&odnBg=ffffff");
//        p1.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
//                "The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', " +
//                "making it look like readable English.");

        model.addAttribute("product", p);
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

    @PostMapping("pay")
    public String pay(@RequestParam String paymentMethod, HttpServletRequest request) {

        if (request.getSession().getAttribute("loggedCustomer") != null) {
            Customer c = customerRepository.findById(((Customer) request.getSession().getAttribute("loggedCustomer")).getUserID()).get();
            Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
            double amount = getCartTotal(cart);

            CustomerOrder o = createorder(cart);

            o.setCustomer(c);
            o.setTotalPrice(getCartTotal(cart));

            for (PaymentMethod paymentMethod1 : c.getPaymentMethods()) {
                if (paymentMethod1.getPaymentID() == Long.valueOf(paymentMethod)) {

                    paymentMethod1.makePayment(amount);
                }
            }

            customerOrderRepository.save(o);


            request.getSession().removeAttribute("cart");

            return "paymentDone";
        } else {
            return "login";
        }
    }

    private CustomerOrder createorder(Map<Product, Integer> cart) {
        CustomerOrder order = new CustomerOrder();

        for (Map.Entry<Product, Integer> cartel : cart.entrySet()) {

            Product p = productRepository.findById(cartel.getKey().getProductID()).get();


            order.getOrderLineList().add(new OrderLine(cartel.getValue(), p));
            cartel.getKey().setQuantity(cartel.getKey().getQuantity() - cartel.getValue());

            p.setQuantity(p.getQuantity() - cartel.getValue());
            productRepository.save(p);
        }

        return order;
    }

    @GetMapping("watch")
    public String watch(@RequestParam("id") String productID, HttpServletRequest request) {
        if (request.getSession().getAttribute("loggedCustomer") == null) {
            return "login";
        }

        Product p = productRepository.findById(Long.valueOf(productID)).get();
        ProductObserverManager.getInstance().addObserver(p, (Customer) request.getSession().getAttribute("loggedCustomer"));

        return "redirect:products";
    }


    @GetMapping("getMessages")
    public ResponseEntity<List<UpdateMessage>> getMessages(HttpServletRequest request) {

        if (request.getSession().getAttribute("loggedCustomer") != null) {
            Customer c = (Customer) request.getSession().getAttribute("loggedCustomer");

            return new ResponseEntity(c.getMessages(), HttpStatus.OK);
        }

        return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("orderHistory")
    public String orderHistory(Model m, HttpServletRequest request) {

        if (request.getSession().getAttribute("loggedCustomer") == null) {
            return "login";
        }

        Iterator<CustomerOrder> it = customerOrderRepository.findAll().iterator();
        List<CustomerOrder> co = new ArrayList<>();
        while (it.hasNext()) {
            Hibernate.initialize(co);
            co.add(it.next());

        }
        m.addAttribute("orders", co);
        return "orderHistory";
    }
}
