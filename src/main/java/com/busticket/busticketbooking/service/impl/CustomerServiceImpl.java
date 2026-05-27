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

/**
 * Concrete implementation of {@link CustomerService}.
 *
 * <p>Handles customer registration, updates, retrieval, and deletion.
 * Validates email uniqueness on creation. Cascades deletion to any reviews
 * or payments left by the customer to preserve referential integrity.</p>
 */
@Service
public class CustomerServiceImpl implements CustomerService {

        /** Repository for customer database operations. */
        private final CustomerRepo customerRepo;

        /** Repository for address database operations. */
        private final AddressRepo addressRepo;

        @Autowired
        private PaymentRepo paymentRepo;

        @Autowired
        private ReviewRepo reviewRepo;

        /**
         * Constructs a CustomerServiceImpl with required repositories.
         *
         * @param customerRepo repository for customer data access
         * @param addressRepo  repository for address data access
         */
        public CustomerServiceImpl(
                        CustomerRepo customerRepo,
                        AddressRepo addressRepo) {
                this.customerRepo = customerRepo;
                this.addressRepo = addressRepo;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public CustomerResponseDTO createCustomer(
                        CustomerRequestDTO customerRequestDTO) {

                // Enforce email uniqueness check
                if (customerRepo.existsByEmail(customerRequestDTO.getEmail())) {
                        throw new DuplicateResourceException(
                                        "Customer with email " + customerRequestDTO.getEmail() + " already exists");
                }

                // Retrieve and validate address
                Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Address with ID " + customerRequestDTO.getAddressId() + " not found"));

                // Map DTO to entity and save
                Customer customer = CustomerMapper.mapToEntity(
                                customerRequestDTO,
                                address);

                Customer savedCustomer = customerRepo.save(customer);
                return CustomerMapper.mapToResponseDTO(savedCustomer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Page<CustomerResponseDTO> getCustomerPage(int page, int size) {
                Pageable pageable = PageRequest.of(page, size);
                return customerRepo.findAll(pageable)
                                .map(CustomerMapper::mapToResponseDTO);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<CustomerResponseDTO> getAllCustomers() {
                return customerRepo.findAll()
                                .stream()
                                .map(CustomerMapper::mapToResponseDTO)
                                .toList();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public CustomerResponseDTO getCustomerById(
                        Integer customerId) {
                Customer customer = customerRepo.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer with ID " + customerId + " not found"));
                return CustomerMapper.mapToResponseDTO(customer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public CustomerResponseDTO updateCustomer(
                        Integer customerId,
                        CustomerRequestDTO customerRequestDTO) {

                Customer customer = customerRepo.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer with ID " + customerId + " not found"));

                Address address = addressRepo.findById(customerRequestDTO.getAddressId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Address with ID " + customerRequestDTO.getAddressId() + " not found"));

                customer.setName(customerRequestDTO.getName());
                customer.setEmail(customerRequestDTO.getEmail());
                customer.setPhone(customerRequestDTO.getPhone());
                customer.setAddress(address);

                Customer updatedCustomer = customerRepo.save(customer);
                return CustomerMapper.mapToResponseDTO(updatedCustomer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional
        public String deleteCustomer(Integer customerId) {

                Customer customer = customerRepo.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer with ID " + customerId + " not found"));

                String customerDetails = "Customer Deleted Successfully : \n" +
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

                // Remove customer record
                customerRepo.delete(customer);

                return customerDetails;
        }
}