// Total tests: 10
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.service.impl.DriverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepo driverRepo;

    @Mock
    private AgencyOfficeRepo officeRepo;

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private DriverServiceImpl driverService;

    private DriverRequestDTO requestDto;
    private AgencyOffice office;
    private Address address;
    private Driver driver;

    @BeforeEach
    public void setUp() {
        requestDto = new DriverRequestDTO();
        requestDto.setOfficeId(1);
        requestDto.setAddressId(1);
        requestDto.setLicenseNumber("DL-12345");
        requestDto.setName("Harpreet Singh");
        requestDto.setPhone("9876543210");

        office = new AgencyOffice();
        office.setId(1);

        address = new Address();
        address.setId(1);
        address.setAddress("123 Main St");
        address.setCity("New Delhi");
        address.setState("Delhi");
        address.setZipCode("110001");
        
        driver = new Driver();
        driver.setId(101);
        driver.setLicenseNumber("DL-12345");
        driver.setName("Harpreet Singh");
        driver.setPhone("9876543210");
        driver.setOffice(office);
        driver.setAddress(address);
    }

    /**
     * 1. testCreateDriver_Success - Verify that a driver is created successfully when valid details are supplied.
     */
    @Test
    public void testCreateDriver_Success() {
        when(driverRepo.existsByLicenseNumber("DL-12345")).thenReturn(false);
        when(officeRepo.findById(1)).thenReturn(Optional.of(office));
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);

        DriverResponseDTO response = driverService.createDriver(requestDto);

        assertNotNull(response);
        assertEquals(101, response.getId());
        assertEquals("DL-12345", response.getLicenseNumber());
        assertEquals("Harpreet Singh", response.getName());
        verify(driverRepo, times(1)).save(any(Driver.class));
    }

    /**
     * 2. testCreateDriver_DuplicateLicense_ThrowsException - Verify that creating a driver with an existing license number throws DuplicateResourceException.
     */
    @Test
    public void testCreateDriver_DuplicateLicense_ThrowsException() {
        when(driverRepo.existsByLicenseNumber("DL-12345")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            driverService.createDriver(requestDto);
        });
        verify(driverRepo, never()).save(any(Driver.class));
    }

    /**
     * 3. testCreateDriver_OfficeNotFound_ThrowsResourceNotFoundException - Verify that creating a driver with a non-existent office throws ResourceNotFoundException.
     */
    @Test
    public void testCreateDriver_OfficeNotFound_ThrowsResourceNotFoundException() {
        when(driverRepo.existsByLicenseNumber("DL-12345")).thenReturn(false);
        when(officeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.createDriver(requestDto);
        });
    }

    /**
     * 4. testCreateDriver_AddressNotFound_ThrowsResourceNotFoundException - Verify that creating a driver with a non-existent address ID throws ResourceNotFoundException.
     */
    @Test
    public void testCreateDriver_AddressNotFound_ThrowsResourceNotFoundException() {
        when(driverRepo.existsByLicenseNumber("DL-12345")).thenReturn(false);
        when(officeRepo.findById(1)).thenReturn(Optional.of(office));
        when(addressRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.createDriver(requestDto);
        });
    }

    /**
     * 5. testGetDriverById_Success - Verify that a driver is successfully retrieved by their unique ID.
     */
    @Test
    public void testGetDriverById_Success() {
        when(driverRepo.findById(101)).thenReturn(Optional.of(driver));

        DriverResponseDTO response = driverService.getDriverById(101);

        assertNotNull(response);
        assertEquals(101, response.getId());
        assertEquals("Harpreet Singh", response.getName());
    }

    /**
     * 6. testGetDriverById_NotFound_ThrowsException - Verify that retrieving a non-existent driver ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetDriverById_NotFound_ThrowsException() {
        when(driverRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.getDriverById(999);
        });
    }

    /**
     * 7. testGetAllDrivers - Verify that all driver records are retrieved successfully.
     */
    @Test
    public void testGetAllDrivers() {
        when(driverRepo.findAll()).thenReturn(Arrays.asList(driver));

        List<DriverResponseDTO> drivers = driverService.getAllDrivers();

        assertNotNull(drivers);
        assertEquals(1, drivers.size());
        assertEquals("Harpreet Singh", drivers.get(0).getName());
    }

    /**
     * 8. testGetDriversByOffice_Success - Verify that drivers belonging to a specific office are retrieved successfully.
     */
    @Test
    public void testGetDriversByOffice_Success() {
        when(driverRepo.findAll()).thenReturn(Arrays.asList(driver));

        List<DriverResponseDTO> drivers = driverService.getDriversByOffice(1);

        assertNotNull(drivers);
        assertEquals(1, drivers.size());
        assertEquals("Harpreet Singh", drivers.get(0).getName());
    }

    /**
     * 9. testUpdateDriver_Success - Verify that a driver's details are updated successfully.
     */
    @Test
    public void testUpdateDriver_Success() {
        when(driverRepo.findById(101)).thenReturn(Optional.of(driver));
        when(officeRepo.findById(1)).thenReturn(Optional.of(office));
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);

        DriverResponseDTO response = driverService.updateDriver(101, requestDto);

        assertNotNull(response);
        assertEquals(101, response.getId());
        verify(driverRepo, times(1)).save(driver);
    }

    /**
     * 10. testDeleteDriver_Success - Verify that a driver record is successfully deleted.
     */
    @Test
    public void testDeleteDriver_Success() {
        when(driverRepo.findById(101)).thenReturn(Optional.of(driver));
        doNothing().when(driverRepo).delete(driver);

        assertAll(() -> driverService.deleteDriver(101));
        verify(driverRepo, times(1)).delete(driver);
    }
}
