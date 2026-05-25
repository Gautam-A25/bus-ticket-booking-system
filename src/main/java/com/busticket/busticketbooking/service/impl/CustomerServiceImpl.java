package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.mapper.CustomerMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.service.CustomerService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer handles customer business logic
@Service
public class CustomerServiceImpl implements CustomerService {

    // Repository object for customer database operations
    private final CustomerRepo customerRepo;

    // Repository object for address database operations
    private final AddressRepo addressRepo;

    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    // Constructor Injection
    public CustomerServiceImpl(
            CustomerRepo customerRepo,
            AddressRepo addressRepo
    ) {
        this.customerRepo = customerRepo;
        this.addressRepo = addressRepo;
    }

    // Creates a new customer record
    @Override
    public CustomerResponseDTO createCustomer(
            CustomerRequestDTO customerRequestDTO
    ) {

        // Checks whether customer email already exists
        if (customerRepo.existsByEmail(customerRequestDTO.getEmail())) {
            throw new DuplicateResourceException("Customer with email " + customerRequestDTO.getEmail() + " already exists");
        }

        // Fetches address using address ID
        Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + customerRequestDTO.getAddressId() + " not found"));

        // Converts DTO object into entity object
        Customer customer = CustomerMapper.mapToEntity(
                customerRequestDTO,
                address
        );

        // Saves customer into database
        Customer savedCustomer = customerRepo.save(customer);

        // Converts entity into response DTO
        return CustomerMapper.mapToResponseDTO(savedCustomer);
    }

    // Fetches customer records using pagination
    @Override
    public Page<CustomerResponseDTO> getCustomerPage(int page, int size) {

        // Creates pageable object using page number and size
        Pageable pageable = PageRequest.of(page, size);

        return customerRepo
                .findAll(pageable)

                // Converts entity objects into response DTOs
                .map(CustomerMapper::mapToResponseDTO);
    }

    // Fetches all customer records
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepo.findAll()
                .stream()

                // Converts entity into response DTO
                .map(CustomerMapper::mapToResponseDTO)

                .toList();
    }

    // Fetches customer details using customer ID
    @Override
    public CustomerResponseDTO getCustomerById(
            Integer customerId
    ) {

        // Fetches customer using customer ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));

        // Converts entity into response DTO
        return CustomerMapper.mapToResponseDTO(customer);
    }

    // Updates existing customer details
    @Override
    public CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO customerRequestDTO
    ) {

        // Fetches customer using customer ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));

        // Fetches address using address ID
        Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + customerRequestDTO.getAddressId() + " not found"));

        // Updates customer name
        customer.setName(customerRequestDTO.getName());

        // Updates customer email
        customer.setEmail(customerRequestDTO.getEmail());

        // Updates customer phone number
        customer.setPhone(customerRequestDTO.getPhone());

        // Updates associated address
        customer.setAddress(address);

        // Saves updated customer into database
        Customer updatedCustomer = customerRepo.save(customer);

        // Converts entity into response DTO
        return CustomerMapper.mapToResponseDTO(updatedCustomer);
    }

    // Deletes customer record using customer ID
    @Override
    @Transactional
    public String deleteCustomer(Integer customerId) {

        // Fetches customer using customer ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + customerId + " not found"));

        // Creates customer deletion message
        String customerDetails =
                "Customer Deleted Successfully : \n" +
                        "ID = " + customer.getId() + "\n" +
                        "Name = " + customer.getName() + "\n" +
                        "Email = " + customer.getEmail() + "\n" +
                        "Phone = " + customer.getPhone() + "\n" +
                        "Address ID = " +
                        (customer.getAddress() != null
                                ? customer.getAddress().getId()
                                : null);

        // Cascade delete: delete customer's reviews first
        List<Review> reviews = reviewRepo.findByCustomerId(customerId);
        reviewRepo.deleteAll(reviews);

        // Cascade delete: delete customer's payments first
        List<Payment> payments = paymentRepo.findByCustomerId(customerId);
        paymentRepo.deleteAll(payments);

        // Delete customer from database
        customerRepo.delete(customer);

         return "Customer Deleted Successfully : ID = "
        + customer.getId()
        + ", Name = "
        + customer.getName()
        + ", Email = "
        + customer.getEmail()
        + ", Phone = "
        + customer.getPhone();
    }
}