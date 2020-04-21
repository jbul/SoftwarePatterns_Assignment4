package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.*;
import com.julie.assignment4.observer.ProductObserverManager;
import com.julie.assignment4.repository.*;
import com.julie.assignment4.sort.SortStrategyFactory;
import com.julie.assignment4.sort.Sorter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/createAccount")
    public String main() {
        return "createAccount";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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


        Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        double cartTotal = getCartTotal(cart);
        double totalDiscount = 0.0;
        if (request.getSession().getAttribute("loggedCustomer") != null) {
            Customer customer = (Customer)request.getSession().getAttribute("loggedCustomer");
            model.addAttribute("customer", customer);

            if (customer.getLoyaltyCard() != null) {

                switch (customer.getLoyaltyCard().getCardType()) {
                    case BRONZE:
                        totalDiscount = 0.05;
                        break;
                    case SILVER:
                        totalDiscount = 0.07;
                        break;
                    case GOLD:
                        totalDiscount = 0.15;
                        break;
                }

                cartTotal =  cartTotal - cartTotal * totalDiscount;
            }
        }

        model.addAttribute("totalDiscount",  (int) (totalDiscount * 100));
        model.addAttribute("products", cart);
        model.addAttribute("total", cartTotal);



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

        List<Review> reviews = reviewRepository.findByProduct(p);

        Hibernate.initialize(p.getCategories());
        Hibernate.initialize(p.getOrderLine());

        model.addAttribute("product", p);
        model.addAttribute("reviews",reviews);

        return "ViewProduct";
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
