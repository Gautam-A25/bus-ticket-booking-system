package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Integer id);

    CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO);

    void deleteCustomer(Integer id);
}