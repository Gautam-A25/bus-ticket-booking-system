package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// Service interface for Customer operations
public interface CustomerService {

    // Method to create customer
    CustomerResponseDTO createCustomer(
            CustomerRequestDTO customerRequestDTO
    );

    Page<CustomerResponseDTO> getCustomerPage(int page, int size);

    // Method to get all customers
    List<CustomerResponseDTO> getAllCustomers();

    // Method to get customer by ID
    CustomerResponseDTO getCustomerById(
            Integer customerId
    );

    // Method to update customer details
    CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO customerRequestDTO
    );

    // Method to delete customer
    String deleteCustomer(Integer customerId);
}