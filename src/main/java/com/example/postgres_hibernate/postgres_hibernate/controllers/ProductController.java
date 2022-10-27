package com.example.postgres_hibernate.postgres_hibernate.controllers;


import com.example.postgres_hibernate.postgres_hibernate.entity.Customer;
import com.example.postgres_hibernate.postgres_hibernate.entity.Product;
import com.example.postgres_hibernate.postgres_hibernate.repository.ProductReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductReposotory productReposotory;

    @GetMapping("/add")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String name){
        try{
            List<Product> products = new ArrayList<>();

            if (name == null)
                productReposotory.findAll().forEach(products::add);
            else
                productReposotory.findByNameContaining(name).forEach(products::add);

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id){
        Optional<Product> productData = productReposotory.findById(id);
        if(productData.isPresent()){
            return new ResponseEntity<>(productData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){

        try {
            Product _product = productReposotory.save(new Product(product.getName(),product.getDescription(),product.getPrice()));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<> (null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product){

        Optional<Product> productData = productReposotory.findById(id);

        if(productData.isPresent()){
            Product _product = productData.get();
            _product.setPrice(product.getPrice());
            _product.setName(product.getName());
            _product.setDescription(product.getDescription());
            return new ResponseEntity<>(productReposotory.save(_product), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/add/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id){
        try {
            productReposotory.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
