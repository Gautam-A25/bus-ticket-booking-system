package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomerById(Integer customerId);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Integer customerId, Customer customer);

    void deleteCustomer(Integer customerId);
}