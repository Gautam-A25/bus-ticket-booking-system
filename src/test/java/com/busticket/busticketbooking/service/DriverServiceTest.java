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

/*
 * @ExtendWith(MockitoExtension.class)
 *
 * Enables Mockito framework in JUnit 5.
 *
 * Mockito is used to create mock objects
 * for unit testing.
 */
@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    /*
     * Mock object for DriverRepo.
     *
     * Real database will NOT be used.
     */
    @Mock
    private DriverRepo driverRepo;

    /*
     * Mock object for AgencyOfficeRepo.
     */
    @Mock
    private AgencyOfficeRepo officeRepo;

    /*
     * Mock object for AddressRepo.
     */
    @Mock
    private AddressRepo addressRepo;

    /*
     * Creates DriverServiceImpl object
     * and injects all mocked dependencies.
     */
    @InjectMocks
    private DriverServiceImpl driverService;

    /*
     * Test data objects.
     */
    private DriverRequestDTO requestDto;
    private AgencyOffice office;
    private Address address;
    private Driver driver;

    /*
     * Runs before every test method.
     *
     * Used to initialize common test data.
     */
    @BeforeEach
    public void setUp() {

        /*
         * Creating DriverRequestDTO object.
         */
        requestDto = new DriverRequestDTO();

        requestDto.setOfficeId(1);
        requestDto.setAddressId(1);
        requestDto.setLicenseNumber("DL-12345");
        requestDto.setName("Harpreet Singh");
        requestDto.setPhone("9876543210");

        /*
         * Creating AgencyOffice object.
         */
        office = new AgencyOffice();
        office.setId(1);

        /*
         * Creating Address object.
         */
        address = new Address();

        address.setId(1);
        address.setAddress("123 Main St");
        address.setCity("New Delhi");
        address.setState("Delhi");
        address.setZipCode("110001");

        /*
         * Creating Driver entity object.
         */
        driver = new Driver();

        driver.setId(101);
        driver.setLicenseNumber("DL-12345");
        driver.setName("Harpreet Singh");
        driver.setPhone("9876543210");
        driver.setOffice(office);
        driver.setAddress(address);
    }

    /*
     * 1. testCreateDriver_Success
     *
     * Verify driver creation works successfully
     * with valid input data.
     */
    @Test
    public void testCreateDriver_Success() {

        /*
         * Mock duplicate check.
         */
        when(driverRepo.existsByLicenseNumber("DL-12345"))
                .thenReturn(false);

        /*
         * Mock office lookup.
         */
        when(officeRepo.findById(1))
                .thenReturn(Optional.of(office));

        /*
         * Mock address lookup.
         */
        when(addressRepo.findById(1))
                .thenReturn(Optional.of(address));

        /*
         * Mock save operation.
         */
        when(driverRepo.save(any(Driver.class)))
                .thenReturn(driver);

        /*
         * Call service method.
         */
        DriverResponseDTO response =
                driverService.createDriver(requestDto);

        /*
         * Verify returned response.
         */
        assertNotNull(response);

        assertEquals(101, response.getId());

        assertEquals(
                "DL-12345",
                response.getLicenseNumber()
        );

        assertEquals(
                "Harpreet Singh",
                response.getName()
        );

        /*
         * Verify save() called exactly once.
         */
        verify(driverRepo, times(1))
                .save(any(Driver.class));
    }

    /*
     * 2. testCreateDriver_DuplicateLicense_ThrowsException
     *
     * Verify exception when duplicate
     * license number exists.
     */
    @Test
    public void testCreateDriver_DuplicateLicense_ThrowsException() {

        /*
         * Mock duplicate license check.
         */
        when(driverRepo.existsByLicenseNumber("DL-12345"))
                .thenReturn(true);

        /*
         * Verify DuplicateResourceException.
         */
        assertThrows(
                DuplicateResourceException.class,
                () -> {
                    driverService.createDriver(requestDto);
                }
        );

        /*
         * Verify save() is never called.
         */
        verify(driverRepo, never())
                .save(any(Driver.class));
    }

    /*
     * 3. testCreateDriver_OfficeNotFound_ThrowsResourceNotFoundException
     *
     * Verify exception when office does not exist.
     */
    @Test
    public void testCreateDriver_OfficeNotFound_ThrowsResourceNotFoundException() {

        /*
         * Mock duplicate check.
         */
        when(driverRepo.existsByLicenseNumber("DL-12345"))
                .thenReturn(false);

        /*
         * Mock missing office.
         */
        when(officeRepo.findById(1))
                .thenReturn(Optional.empty());

        /*
         * Verify ResourceNotFoundException.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> {
                    driverService.createDriver(requestDto);
                }
        );
    }

    /*
     * 4. testCreateDriver_AddressNotFound_ThrowsResourceNotFoundException
     *
     * Verify exception when address does not exist.
     */
    @Test
    public void testCreateDriver_AddressNotFound_ThrowsResourceNotFoundException() {

        /*
         * Mock duplicate check.
         */
        when(driverRepo.existsByLicenseNumber("DL-12345"))
                .thenReturn(false);

        /*
         * Mock office lookup.
         */
        when(officeRepo.findById(1))
                .thenReturn(Optional.of(office));

        /*
         * Mock missing address.
         */
        when(addressRepo.findById(1))
                .thenReturn(Optional.empty());

        /*
         * Verify ResourceNotFoundException.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> {
                    driverService.createDriver(requestDto);
                }
        );
    }

    /*
     * 5. testGetDriverById_Success
     *
     * Verify fetching driver by ID works correctly.
     */
    @Test
    public void testGetDriverById_Success() {

        /*
         * Mock repository response.
         */
        when(driverRepo.findById(101))
                .thenReturn(Optional.of(driver));

        /*
         * Call service method.
         */
        DriverResponseDTO response =
                driverService.getDriverById(101);

        /*
         * Verify response.
         */
        assertNotNull(response);

        assertEquals(101, response.getId());

        assertEquals(
                "Harpreet Singh",
                response.getName()
        );
    }

    /*
     * 6. testGetDriverById_NotFound_ThrowsException
     *
     * Verify exception when driver ID does not exist.
     */
    @Test
    public void testGetDriverById_NotFound_ThrowsException() {

        /*
         * Mock empty repository response.
         */
        when(driverRepo.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify ResourceNotFoundException.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> {
                    driverService.getDriverById(999);
                }
        );
    }

    /*
     * 7. testGetAllDrivers
     *
     * Verify all drivers are fetched successfully.
     */
    @Test
    public void testGetAllDrivers() {

        /*
         * Mock repository response.
         */
        when(driverRepo.findAll())
                .thenReturn(Arrays.asList(driver));

        /*
         * Call service method.
         */
        List<DriverResponseDTO> drivers =
                driverService.getAllDrivers();

        /*
         * Verify response list.
         */
        assertNotNull(drivers);

        assertEquals(1, drivers.size());

        assertEquals(
                "Harpreet Singh",
                drivers.get(0).getName()
        );
    }

    /*
     * 8. testGetDriversByOffice_Success
     *
     * Verify drivers are filtered correctly
     * using office ID.
     */
    @Test
    public void testGetDriversByOffice_Success() {

        /*
         * Mock repository response.
         */
        when(driverRepo.findAll())
                .thenReturn(Arrays.asList(driver));

        /*
         * Call service method.
         */
        List<DriverResponseDTO> drivers =
                driverService.getDriversByOffice(1);

        /*
         * Verify filtered response.
         */
        assertNotNull(drivers);

        assertEquals(1, drivers.size());

        assertEquals(
                "Harpreet Singh",
                drivers.get(0).getName()
        );
    }

    /*
     * 9. testUpdateDriver_Success
     *
     * Verify updating driver works successfully.
     */
    @Test
    public void testUpdateDriver_Success() {

        /*
         * Mock repository responses.
         */
        when(driverRepo.findById(101))
                .thenReturn(Optional.of(driver));

        when(officeRepo.findById(1))
                .thenReturn(Optional.of(office));

        when(addressRepo.findById(1))
                .thenReturn(Optional.of(address));

        when(driverRepo.save(any(Driver.class)))
                .thenReturn(driver);

        /*
         * Call update method.
         */
        DriverResponseDTO response =
                driverService.updateDriver(101, requestDto);

        /*
         * Verify updated response.
         */
        assertNotNull(response);

        assertEquals(101, response.getId());

        /*
         * Verify save() called once.
         */
        verify(driverRepo, times(1))
                .save(driver);
    }

    /*
     * 10. testDeleteDriver_Success
     *
     * Verify driver deletion works successfully.
     */
    @Test
    public void testDeleteDriver_Success() {

        /*
         * Mock repository response.
         */
        when(driverRepo.findById(101))
                .thenReturn(Optional.of(driver));

        /*
         * doNothing() used for void methods.
         */
        doNothing().when(driverRepo).delete(driver);

        /*
         * Verify no exception occurs.
         */
        assertAll(() -> driverService.deleteDriver(101));

        /*
         * Verify delete() called once.
         */
        verify(driverRepo, times(1))
                .delete(driver);
    }
}