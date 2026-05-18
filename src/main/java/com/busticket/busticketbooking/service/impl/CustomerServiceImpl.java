package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.CustomerDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.service.CustomerService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        Address address = addressRepo.findById(customerDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(address);

        Customer savedCustomer = customerRepo.save(customer);

        return mapToDTO(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Address address = addressRepo.findById(customerDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(address);

        Customer updatedCustomer = customerRepo.save(customer);

        return mapToDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Integer id) {

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepo.delete(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress().getId()
        );
    }
}