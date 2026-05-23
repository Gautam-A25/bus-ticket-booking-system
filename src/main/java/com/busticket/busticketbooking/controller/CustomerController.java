package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller layer handles customer-related APIs
@RestController

// Base URL for customer APIs
@RequestMapping("/api/v1")
public class CustomerController {

    // Service layer object for customer business logic
    private final CustomerService customerService;

    // Constructor injection for dependency injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Creates a new customer record
    @PostMapping("/customers")
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.createCustomer(customerRequestDTO);
    }

    // Fetches all customer records
    @GetMapping("/customers")
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    // Fetches customer details using customer ID
    @GetMapping("/customers/{customerId}")
    public CustomerResponseDTO getCustomerById(
            @PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId);
    }

    // Updates existing customer details
    @PutMapping("/customers/{customerId}")
    public CustomerResponseDTO updateCustomer(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.updateCustomer(
                customerId,
                customerRequestDTO
        );
    }

    // Deletes customer record using customer ID
    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(
            @PathVariable Integer customerId
    ) {

        return customerService.deleteCustomer(customerId);
    }
}