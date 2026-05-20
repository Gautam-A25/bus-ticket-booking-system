// Total tests: 11
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerRequestDTO requestDto;
    private Address address;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        requestDto = new CustomerRequestDTO();
        requestDto.setName("Aman Sharma");
        requestDto.setEmail("aman@gmail.com");
        requestDto.setPhone("9988776655");
        requestDto.setAddressId(1);

        address = new Address();
        address.setId(1);

        customer = new Customer();
        customer.setId(10);
        customer.setName("Aman Sharma");
        customer.setEmail("aman@gmail.com");
        customer.setPhone("9988776655");
        customer.setAddress(address);
    }

    /**
     * 1. testCreateCustomer_Success - Verify that a customer is created successfully under a valid address.
     */
    @Test
    public void testCreateCustomer_Success() {
        when(customerRepo.existsByEmail("aman@gmail.com")).thenReturn(false);
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO response = customerService.createCustomer(requestDto);

        assertNotNull(response);
        assertEquals(10, response.getId());
        assertEquals("Aman Sharma", response.getName());
    }

    /**
     * 2. testCreateCustomer_DuplicateEmail_ThrowsException - Verify duplicate emails throw DuplicateResourceException.
     */
    @Test
    public void testCreateCustomer_DuplicateEmail_ThrowsException() {
        when(customerRepo.existsByEmail("aman@gmail.com")).thenReturn(true);
        assertThrows(DuplicateResourceException.class, () -> customerService.createCustomer(requestDto));
    }

    /**
     * 3. testCreateCustomer_AddressNotFound_ThrowsException - Verify missing Address ID throws ResourceNotFoundException.
     */
    @Test
    public void testCreateCustomer_AddressNotFound_ThrowsException() {
        when(customerRepo.existsByEmail("aman@gmail.com")).thenReturn(false);
        when(addressRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.createCustomer(requestDto));
    }

    /**
     * 4. testGetCustomerById_Success - Verify customer is retrieved by ID.
     */
    @Test
    public void testGetCustomerById_Success() {
        when(customerRepo.findById(10)).thenReturn(Optional.of(customer));
        CustomerResponseDTO response = customerService.getCustomerById(10);
        assertNotNull(response);
        assertEquals(10, response.getId());
    }

    /**
     * 5. testGetCustomerById_NotFound_ThrowsException - Verify missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetCustomerById_NotFound_ThrowsException() {
        when(customerRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(999));
    }

    /**
     * 6. testGetAllCustomers_NotEmpty - Verify retrieving all registered customer records.
     */
    @Test
    public void testGetAllCustomers_NotEmpty() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        List<CustomerResponseDTO> response = customerService.getAllCustomers();
        assertEquals(1, response.size());
    }

    /**
     * 7. testGetAllCustomers_Empty - Verify that empty list behaves nicely.
     */
    @Test
    public void testGetAllCustomers_Empty() {
        when(customerRepo.findAll()).thenReturn(Collections.emptyList());
        List<CustomerResponseDTO> response = customerService.getAllCustomers();
        assertTrue(response.isEmpty());
    }

    /**
     * 8. testUpdateCustomer_Success - Verify updating name, email, and phone successfully.
     */
    @Test
    public void testUpdateCustomer_Success() {
        when(customerRepo.findById(10)).thenReturn(Optional.of(customer));
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        CustomerResponseDTO response = customerService.updateCustomer(10, requestDto);
        assertNotNull(response);
        assertEquals("Aman Sharma", response.getName());
    }

    /**
     * 9. testUpdateCustomer_NotFound_ThrowsException - Verify updating a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testUpdateCustomer_NotFound_ThrowsException() {
        when(customerRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(999, requestDto));
    }

    /**
     * 10. testDeleteCustomer_Success - Verify that deleting a customer completes successfully.
     */
    @Test
    public void testDeleteCustomer_Success() {
        when(customerRepo.findById(10)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepo).delete(customer);
        String response = customerService.deleteCustomer(10);
        assertEquals(
                "Customer Deleted Successfully : " +
                        "ID = 10" +
                        ", Name = Aman Sharma" +
                        ", Email = aman@gmail.com" +
                        ", Phone = 9988776655",
                response
        );
    }

    /**
     * 11. testDeleteCustomer_NotFound_ThrowsException - Verify deleting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testDeleteCustomer_NotFound_ThrowsException() {
        when(customerRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(999));
    }
}
