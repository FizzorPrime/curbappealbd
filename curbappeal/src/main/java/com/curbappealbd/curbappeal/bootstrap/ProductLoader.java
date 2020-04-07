package com.curbappealbd.curbappeal.bootstrap;

import com.curbappealbd.curbappeal.domain.Product;
import com.curbappealbd.curbappeal.repositories.AuthorRepository;
import com.curbappealbd.curbappeal.repositories.BookRepository;
import com.curbappealbd.curbappeal.repositories.ProductRepository;
import com.curbappealbd.curbappeal.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

//    private final AuthorRepository authorRepository;
//    private final BookRepository bookRepository;
//    private final PublisherRepository publisherRepository;
    private ProductRepository productRepository;

//    private Logger log = Logger.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Product shirt = new Product("Spring Framework Guru Shirt", new BigDecimal("18.95"), "https://", "123456");
        productRepository.save(shirt);

//        log.info("Saved Shirt - id: " + shirt.getId());
        System.out.println("Saved Shirt - id: " + shirt.getId());

        Product mug = new Product("Spring Framework Guru Mug", new BigDecimal("11.95"), "https://", "0987654");
        productRepository.save(mug);

//        log.info("Saved Mug - id: " + mug.getId());
        System.out.println("Saved Mug - id: " + mug.getId());
    }

}
