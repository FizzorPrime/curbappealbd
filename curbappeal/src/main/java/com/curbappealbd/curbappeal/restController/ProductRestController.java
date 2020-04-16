package com.curbappealbd.curbappeal.restController;

import java.math.BigDecimal;

import com.curbappealbd.curbappeal.repositories.ProductRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.curbappealbd.curbappeal.domain.Product;

@RestController
public class ProductRestController implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        product.setId(productRepository.count() + 1);
        for (int i=1; i <= productRepository.count(); i++) {
            if(productRepository.existsById(Integer.toUnsignedLong(i))){
                continue;
            }
            else{
                product.setId((long) i);
                break;
            }
        }
        productRepository.save(product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    // Read
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    // Update
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productRepository.deleteById(id);
        product.setId(id);
        productRepository.save(product);
        return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
    }

    // Delete
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Product honey = new Product(1L, "Honey", new BigDecimal("8.95"), "https://honey.com", "1");
        Product almond = new Product(2L, "Almond", new BigDecimal("12.95"), "https://almonds.com", "2");
        productRepository.save(honey);
        productRepository.save(almond);
    }
}

