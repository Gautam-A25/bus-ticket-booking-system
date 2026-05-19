package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.customerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.customerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.mapper.CustomerMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

// Marks this class as Service layer component
@Service
public class CustomerServiceImpl implements CustomerService {

    // Repository dependency for Customer table
    private final CustomerRepo customerRepo;

    // Repository dependency for Address table
    private final AddressRepo addressRepo;

    // Constructor Injection
    public CustomerServiceImpl(
            CustomerRepo customerRepo,
            AddressRepo addressRepo
    ) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
    }

    // Method to create customer
    @Override
    public CustomerResponseDTO createCustomer(
            CustomerRequestDTO customerRequestDTO
    ) {

        // Fetch address by ID
        Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Convert DTO to Entity
        Customer customer = CustomerMapper.mapToEntity(
                customerRequestDTO,
                address
        );

        // Save customer into database
        Customer savedCustomer = customerRepo.save(customer);

        // Convert Entity to Response DTO
        return CustomerMapper.mapToResponseDTO(savedCustomer);
    }

    // Method to get all customers
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepo.findAll()
                .stream()

                // Convert Entity to Response DTO
                .map(CustomerMapper::mapToResponseDTO)

                .toList();
    }

    // Method to get customer by ID
    @Override
    public CustomerResponseDTO getCustomerById(
            Integer customerId
    ) {

        // Fetch customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Convert Entity to Response DTO
        return CustomerMapper.mapToResponseDTO(customer);
    }

    // Method to update customer details
    @Override
    public CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO customerRequestDTO
    ) {

        // Fetch customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Fetch address by ID
        Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Update customer name
        customer.setName(customerRequestDTO.getName());

        // Update customer email
        customer.setEmail(customerRequestDTO.getEmail());

        // Update customer phone number
        customer.setPhone(customerRequestDTO.getPhone());

        // Update customer address
        customer.setAddress(address);

        // Save updated customer into database
        Customer updatedCustomer = customerRepo.save(customer);

        // Convert Entity to Response DTO
        return CustomerMapper.mapToResponseDTO(updatedCustomer);
    }

    // Method to delete customer
    @Override
    public String deleteCustomer(Integer customerId) {

        // Fetch customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Delete customer from database
        customerRepo.delete(customer);

        return "Customer with ID " + customerId +
                " deleted successfully";
    }
}