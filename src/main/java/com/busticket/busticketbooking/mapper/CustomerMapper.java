package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;

// Mapper class for Customer entity and DTO conversion
public class CustomerMapper {

    // Converts CustomerRequestDTO to Customer Entity
    public static Customer mapToEntity(
            CustomerRequestDTO customerRequestDTO,
            Address address
    ) {

        // Create new Customer entity object
        Customer customer = new Customer();

        // Set customer name
        customer.setName(customerRequestDTO.getName());

        // Set customer email
        customer.setEmail(customerRequestDTO.getEmail());

        // Set customer phone number
        customer.setPhone(customerRequestDTO.getPhone());

        // Set customer address
        customer.setAddress(address);

        return customer;
    }

    // Converts Customer Entity to CustomerResponseDTO
    public static CustomerResponseDTO mapToResponseDTO(
            Customer customer
    ) {

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress().getId()
        );
    }
}