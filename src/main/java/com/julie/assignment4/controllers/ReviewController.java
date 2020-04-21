package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.Customer;
import com.julie.assignment4.entity.Review;
import com.julie.assignment4.repository.CustomerRepository;
import com.julie.assignment4.repository.ProductRepository;
import com.julie.assignment4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReviewController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @PostMapping("/reviewProduct")
    public String reviewProduct(@RequestParam int reviewScore, @RequestParam String formCommentReview, @RequestParam long productID, HttpServletRequest request){

        Customer customer = (Customer)request.getSession().getAttribute("loggedCustomer");
        Customer currentCustomer = customerRepository.findById(Long.valueOf(customer.getUserID())).get();
        Review review = new Review();
        review.setCustomer(currentCustomer);
        review.setScore(reviewScore);
        review.setComment(formCommentReview);
        review.setProduct(productRepository.findById(productID).get());

        reviewRepository.save(review);


        return "redirect:viewProduct?id=" + productID;
    }



}
