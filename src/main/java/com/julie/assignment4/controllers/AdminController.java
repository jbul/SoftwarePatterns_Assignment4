package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.Admin;
import com.julie.assignment4.entity.Product;
import com.julie.assignment4.entity.User;
import com.julie.assignment4.observer.ProductObserverManager;
import com.julie.assignment4.repository.AdminRepository;
import com.julie.assignment4.repository.ProductRepository;
import com.julie.assignment4.sort.SortStrategyFactory;
import com.julie.assignment4.sort.Sorter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller("admin")
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AdminRepository adminRepository;

    private boolean isUserAdmin(User u) {
        if (u == null) return false;
        return (u instanceof Admin);
    }

    @GetMapping("adminProducts")
    public String adminProducts(@PathParam("order") String order,
                                @PathParam("name") String name,
                                Model model, HttpServletRequest request) {

        User u = (User) request.getSession().getAttribute("loggedUser");
        if (isUserAdmin(u)) {

            List<Product> products = new ArrayList<>();

            for (Product p : productRepository.findAll()) {
                products.add(p);
            }

            if (name == null) {
                name = "";
            }

            if (order == null) {
                order = "asc";
            }

            Sorter sorter = new Sorter(products, SortStrategyFactory.getSortStrategy(name));
            model.addAttribute("products", sorter.sort(order.equals("asc")));

            return "adminProducts";
        }
        return "login";
    }

    @PostMapping("updateProduct")
    public String updateProduct(Product product) {
        boolean wasRestocked = false;
        Product p = productRepository.findById(product.getProductID()).get();

        p.setDescription(product.getDescription());
        p.setProductTitle(product.getProductTitle());
        p.setPrice(product.getPrice());

        if (p.getQuantity() == 0 && product.getQuantity() > 0) {
            wasRestocked = true;
        }

        p.setQuantity(product.getQuantity());
        productRepository.save(p);

        if (wasRestocked) {
            ProductObserverManager.getInstance().productChanged(product);
        }
        return "redirect:adminProducts";
    }

    @GetMapping("editProduct")
    public String editProduct(@RequestParam("id") String productId,
                              Model model) {

        Product p = productRepository.findById(Long.valueOf(productId)).get();

        Hibernate.initialize(p.getCategories());
        Hibernate.initialize(p.getOrderLine());

        model.addAttribute("product", p);

        return "editProduct";

    }


    @Transactional
    @PostMapping("/login")
    public String userLogin(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {


        Admin a =adminRepository.getByEmail(email);

        if (a != null && a.getPassword().equals(password)) {

            request.getSession().setAttribute("loggedUser", a);
            return "redirect:adminProducts";
        }else {
            return "login";
        }
    }

}
