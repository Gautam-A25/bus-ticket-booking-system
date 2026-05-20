package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as REST Controller
@RestController

// Base URL for Customer APIs
@RequestMapping("/api/v1")
public class CustomerController {

    // Service layer dependency
    private final CustomerService customerService;

    // Constructor Injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // API to create customer
    @PostMapping("/customers")
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.createCustomer(customerRequestDTO);
    }

    // API to get all customers
    @GetMapping("/customers")
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // API to get customer by ID
    @GetMapping("/customers/{customerId}")
    public CustomerResponseDTO getCustomerById(
            @PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId);
    }

    // API to update customer details
    @PutMapping("/customers/{customerId}")
    public CustomerResponseDTO updateCustomer(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.updateCustomer(
                customerId,
                customerRequestDTO
        );
    }

    // API to delete customer
    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(
            @PathVariable Integer customerId
    ) {

        return customerService.deleteCustomer(customerId);
    }
}