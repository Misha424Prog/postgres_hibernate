package com.example.postgres_hibernate.postgres_hibernate.controllers;

import com.example.postgres_hibernate.postgres_hibernate.entity.Customer;
import com.example.postgres_hibernate.postgres_hibernate.entity.Product;
import com.example.postgres_hibernate.postgres_hibernate.entity.Purchase;
import com.example.postgres_hibernate.postgres_hibernate.repository.CustomerRepository;
import com.example.postgres_hibernate.postgres_hibernate.repository.ProductReposotory;
import com.example.postgres_hibernate.postgres_hibernate.repository.PuchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/customer")
public class MainController {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PuchaseRepository puchaseRepository;

    @Autowired
    ProductReposotory productReposotory;

    @GetMapping("/add")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String name) {
        try {
            List<Customer> customers = new ArrayList<Customer>();

            if (name == null)
                customerRepository.findAll().forEach(customers::add);
            else
                customerRepository.findByNameContaining(name).forEach(customers::add);

            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/add/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer _customer = customerRepository.save(new Customer(customer.getName(), customer.getLastName(), customer.getAge(),customer.getSumBuy()));
            return new ResponseEntity<>(_customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setLastName(customer.getLastName());
            _customer.setAge(customer.getAge());
            return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/add/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("add/{id}/buy/{productId}")
    public ResponseEntity<Purchase> buyProduct(@PathVariable("id") long id, @PathVariable("productId") long productId){
        try{
            Optional<Customer> customerData = customerRepository.findById(id);
            Optional<Product> productData = productReposotory.findById(productId);
            if(customerData.isPresent() && productData.isPresent()){
                Customer _customer = customerData.get();
                Product _product = productData.get();
                Date currentDate = new Date();
                Purchase purchase = puchaseRepository.save(new Purchase(currentDate,_customer,_product.getPrice()));
                _customer.setSumBuy(_customer.getSumBuy()+_product.getPrice());
                customerRepository.save(_customer);
                return new ResponseEntity<>(purchase, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ArrayList<Product> productsCan = new ArrayList<>();
    @PostMapping("add/{id}/can/{productId}")
    public ResponseEntity<HttpStatus> addToCan(@PathVariable("id") long id, @PathVariable("productId") long productId){
        Optional<Customer> customerData = customerRepository.findById(id);
        Optional<Product> productData = productReposotory.findById(productId);
        if(customerData.isPresent() && productData.isPresent()) {
            productsCan.add(productData.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("add/{id}/can")
    public ResponseEntity<ArrayList<Purchase>> buyCan(@PathVariable("id") long id){
        Optional<Customer> customerData = customerRepository.findById(id);
        if(customerData.isPresent()&& productsCan!=null){

            ArrayList<Purchase> purchases = new ArrayList<>();
            for(Product pr: productsCan){
                Date currentDate = new Date();
                Purchase purchase = new Purchase(currentDate,customerData.get(),pr.getPrice());
                purchases.add(purchase);
                Customer customer = customerData.get();
                customer.setSumBuy(customer.getSumBuy()+pr.getPrice());
                customerRepository.save(customer);
                puchaseRepository.save(purchase);

            }
            return new ResponseEntity<>(purchases,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}

