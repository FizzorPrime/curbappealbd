package com.curbappealbd.curbappeal.controllers;

import com.curbappealbd.curbappeal.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) { this.productRepository = productRepository; }

    @RequestMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/list";
    }
}
