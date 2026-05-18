package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.CustomerDTO;
import com.busticket.busticketbooking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO createCustomer(
            @Valid @RequestBody CustomerDTO customerDTO) {

        return customerService.createCustomer(customerDTO);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Integer id) {

        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerDTO customerDTO) {

        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Integer id) {

        customerService.deleteCustomer(id);

        return "Customer deleted successfully";
    }
}