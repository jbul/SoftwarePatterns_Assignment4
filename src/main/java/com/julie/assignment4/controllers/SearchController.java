package com.julie.assignment4.controllers;

import com.julie.assignment4.entity.Category;
import com.julie.assignment4.entity.Product;
import com.julie.assignment4.repository.CategoryRepository;
import com.julie.assignment4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping
    public String search(@RequestParam String item, @RequestParam String search, Model model) {
        List<Product> productList = new ArrayList<>();

        switch (item) {
            case "productTitle":
                productList.addAll(productRepository.findByProductTitleContaining(search));
                break;
            case "manufacturer":
                productList.addAll(productRepository.findByManufacturerContaining(search));
                break;
            case "category":
                String[] categories = search.split(",");
                List<Category> categoryList = new ArrayList<>();
                for (String category : categories) {
                    categoryList.add(categoryRepository.findByCategoryTitle(category));
                }
                productList.addAll(productRepository.findByCategoriesIn(categoryList));
                break;
        }

        model.addAttribute("products", productList);

        return "products";
    }
}
