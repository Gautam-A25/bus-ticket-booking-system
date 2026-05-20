// Total tests: 9
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.service.impl.AddressServiceImpl;
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
public class AddressServiceTest {

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    private AddressAddressRequestDTO requestDto;
    private Address address;

    @BeforeEach
    public void setUp() {
        requestDto = new AddressAddressRequestDTO();
        requestDto.setAddress("123 Main St");
        requestDto.setCity("Mumbai");
        requestDto.setState("Maharashtra");
        requestDto.setZipCode("400001");

        address = new Address();
        address.setId(1);
        address.setAddress("123 Main St");
        address.setCity("Mumbai");
        address.setState("Maharashtra");
        address.setZipCode("400001");
    }

    /**
     * 1. testAddAddress_Success - Verify that a new address is successfully created.
     */
    @Test
    public void testAddAddress_Success() {
        when(addressRepo.save(any(Address.class))).thenReturn(address);
        AddressResponseDTO response = addressService.addAddress(requestDto);
        assertNotNull(response);
        assertEquals(1, response.getAddressId());
        assertEquals("123 Main St", response.getAddress());
    }

    /**
     * 2. testGetAddressById_Success - Verify that an address is successfully retrieved by ID.
     */
    @Test
    public void testGetAddressById_Success() {
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        AddressResponseDTO response = addressService.getAddressById(1);
        assertNotNull(response);
        assertEquals(1, response.getAddressId());
    }

    /**
     * 3. testGetAddressById_NotFound_ThrowsException - Verify that requesting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetAddressById_NotFound_ThrowsException() {
        when(addressRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(999));
    }

    /**
     * 4. testGetAllAddresses_NotEmpty - Verify that list of addresses is successfully retrieved.
     */
    @Test
    public void testGetAllAddresses_NotEmpty() {
        when(addressRepo.findAll()).thenReturn(Arrays.asList(address));
        List<AddressResponseDTO> response = addressService.getAllAddresses();
        assertEquals(1, response.size());
    }

    /**
     * 5. testGetAllAddresses_Empty - Verify that an empty list is handled properly.
     */
    @Test
    public void testGetAllAddresses_Empty() {
        when(addressRepo.findAll()).thenReturn(Collections.emptyList());
        List<AddressResponseDTO> response = addressService.getAllAddresses();
        assertTrue(response.isEmpty());
    }

    /**
     * 6. testUpdateAddress_Success - Verify that an address is updated successfully.
     */
    @Test
    public void testUpdateAddress_Success() {
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(addressRepo.save(any(Address.class))).thenReturn(address);
        AddressResponseDTO response = addressService.updateAddress(1, requestDto);
        assertNotNull(response);
        assertEquals("123 Main St", response.getAddress());
    }

    /**
     * 7. testUpdateAddress_NotFound_ThrowsException - Verify that updating a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testUpdateAddress_NotFound_ThrowsException() {
        when(addressRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(999, requestDto));
    }

    /**
     * 8. testDeleteAddress_Success - Verify that deleting an existing ID returns success message.
     */
    @Test
    public void testDeleteAddress_Success() {
        when(addressRepo.existsById(1)).thenReturn(true);
        doNothing().when(addressRepo).deleteById(1);
        String response = addressService.deleteAddress(1);
        assertEquals("Address deleted successfully", response);
    }

    /**
     * 9. testDeleteAddress_NotFound_ThrowsException - Verify that deleting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testDeleteAddress_NotFound_ThrowsException() {
        when(addressRepo.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(999));
    }

    // Helper static nested class since target file imports AddressRequestDTO
    private static class AddressAddressRequestDTO extends AddressRequestDTO {}
}
