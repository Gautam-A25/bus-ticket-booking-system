package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all customer management operations.
 *
 * <p>A {@code Customer} is registered in the system with contact details, a unique email,
 * and an associated {@code Address}. Customers place {@code Booking}s and submit {@code Review}s.
 * Deleting a customer will cascade delete all their associated reviews and payments.</p>
 */
public interface CustomerService {

    /**
     * Creates and persists a new customer record.
     *
     * @param customerRequestDTO details of the customer to be registered
     * @return a {@link CustomerResponseDTO} representing the created customer
     * @throws com.busticket.busticketbooking.exception.DuplicateResourceException if a customer with the email already exists
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the associated address is not found
     */
    CustomerResponseDTO createCustomer(
            CustomerRequestDTO customerRequestDTO
    );

    /**
     * Retrieves a paginated slice of all customer records.
     *
     * @param page zero-based page index
     * @param size maximum number of customers per page
     * @return a {@link Page} of customer response DTOs
     */
    Page<CustomerResponseDTO> getCustomerPage(int page, int size);

    /**
     * Retrieves all customer records in the system (no pagination).
     *
     * @return a list of all customer response DTOs
     */
    List<CustomerResponseDTO> getAllCustomers();

    /**
     * Retrieves customer details by their ID.
     *
     * @param customerId ID of the customer to retrieve
     * @return the customer response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the customer is not found
     */
    CustomerResponseDTO getCustomerById(
            Integer customerId
    );

    /**
     * Updates an existing customer's details.
     *
     * @param customerId         ID of the customer to update
     * @param customerRequestDTO the new details of the customer
     * @return the updated customer response DTO
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the customer or new address is not found
     */
    CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO customerRequestDTO
    );

    /**
     * Deletes a customer record from the system, cascading the deletion to their reviews and payments.
     *
     * @param customerId ID of the customer to delete
     * @return a formatted deletion summary message
     * @throws com.busticket.busticketbooking.exception.ResourceNotFoundException if the customer is not found
     */
    String deleteCustomer(Integer customerId);
}