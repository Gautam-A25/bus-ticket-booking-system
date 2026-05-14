package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(
            @PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/{customerId}")
    public Customer updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody Customer customer) {

        return customerService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(
            @PathVariable Integer customerId) {

        customerService.deleteCustomer(customerId);

        return "Customer deleted successfully";
    }
}