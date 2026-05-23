package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

// Service interface defines customer business operations
public interface CustomerService {

    // Creates a new customer record
    CustomerResponseDTO createCustomer(
            CustomerRequestDTO customerRequestDTO
    );

    // Fetches customer records using pagination
    Page<CustomerResponseDTO> getCustomerPage(int page, int size);

    // Fetches all customer records
    List<CustomerResponseDTO> getAllCustomers();

    // Fetches customer details using customer ID
    CustomerResponseDTO getCustomerById(
            Integer customerId
    );

    // Updates existing customer details
    CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO customerRequestDTO
    );

    // Deletes customer record using customer ID
    String deleteCustomer(Integer customerId);
}