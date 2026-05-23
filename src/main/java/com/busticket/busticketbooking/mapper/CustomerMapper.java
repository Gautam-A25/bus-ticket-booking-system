package com.busticket.busticketbooking.mapper;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;

// Mapper class used for Customer entity and DTO conversion
public class CustomerMapper {

    // Converts CustomerRequestDTO into Customer entity object
    public static Customer mapToEntity(
            CustomerRequestDTO customerRequestDTO,
            Address address
    ) {

        // Creates new Customer entity object
        Customer customer = new Customer();

        // Sets customer name
        customer.setName(customerRequestDTO.getName());

        // Sets customer email address
        customer.setEmail(customerRequestDTO.getEmail());

        // Sets customer phone number
        customer.setPhone(customerRequestDTO.getPhone());

        // Sets associated address object
        customer.setAddress(address);

        return customer;
    }

    // Converts Customer entity into CustomerResponseDTO
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