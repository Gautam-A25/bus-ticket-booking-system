// Total tests: 6
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDto.DriverRequestDto;
import com.busticket.busticketbooking.dto.DriverDto.DriverResponseDto;
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
public class DriverServiceImplTest {

    @Mock
    private DriverRepo driverRepo;

    @Mock
    private AgencyOfficeRepo officeRepo;

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private DriverServiceImpl driverService;

    private DriverRequestDto requestDto;
    private AgencyOffice office;
    private Address address;
    private Driver driver;

    @BeforeEach
    public void setUp() {
        requestDto = new DriverRequestDto();
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
     * testCreateDriver_Success - Verify that a driver is created successfully when valid details are supplied.
     */
    @Test
    public void testCreateDriver_Success() {
        when(driverRepo.existsByLicenseNumber("DL-12345")).thenReturn(false);
        when(officeRepo.findById(1)).thenReturn(Optional.of(office));
        when(addressRepo.findById(1)).thenReturn(Optional.of(address));
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);

        DriverResponseDto response = driverService.createDriver(requestDto);

        assertNotNull(response);
        assertEquals(101, response.getId());
        assertEquals("DL-12345", response.getLicenseNumber());
        assertEquals("Harpreet Singh", response.getName());
        verify(driverRepo, times(1)).save(any(Driver.class));
    }

    /**
     * testCreateDriver_DuplicateLicense_ThrowsException - Verify that creating a driver with an existing license number throws DuplicateResourceException.
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
     * testGetDriverById_Success - Verify that a driver is successfully retrieved by their unique ID.
     */
    @Test
    public void testGetDriverById_Success() {
        when(driverRepo.findById(101)).thenReturn(Optional.of(driver));

        DriverResponseDto response = driverService.getDriverById(101);

        assertNotNull(response);
        assertEquals(101, response.getId());
        assertEquals("Harpreet Singh", response.getName());
    }

    /**
     * testGetDriverById_NotFound_ThrowsException - Verify that retrieving a non-existent driver ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetDriverById_NotFound_ThrowsException() {
        when(driverRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.getDriverById(999);
        });
    }

    /**
     * testGetAllDrivers - Verify that all driver records are retrieved successfully.
     */
    @Test
    public void testGetAllDrivers() {
        when(driverRepo.findAll()).thenReturn(Arrays.asList(driver));

        List<DriverResponseDto> drivers = driverService.getAllDrivers();

        assertNotNull(drivers);
        assertEquals(1, drivers.size());
        assertEquals("Harpreet Singh", drivers.get(0).getName());
    }

    /**
     * testDeleteDriver_Success - Verify that a driver record is successfully deleted.
     */
    @Test
    public void testDeleteDriver_Success() {
        when(driverRepo.findById(101)).thenReturn(Optional.of(driver));
        doNothing().when(driverRepo).delete(driver);

        assertAll(() -> driverService.deleteDriver(101));
        verify(driverRepo, times(1)).delete(driver);
    }
}
