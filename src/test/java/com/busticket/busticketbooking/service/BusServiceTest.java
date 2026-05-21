package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.impl.BusServiceImpl;

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

/*
 * @ExtendWith(MockitoExtension.class)
 *
 * Enables Mockito framework in JUnit 5.
 *
 * Mockito is used to create mock objects
 * for unit testing.
 */
@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    /*
     * @Mock creates fake/mock object
     * of BusRepo.
     *
     * Actual database will NOT be used.
     */
    @Mock
    private BusRepo busRepo;

    /*
     * Mock object for AgencyOfficeRepo.
     */
    @Mock
    private AgencyOfficeRepo officeRepo;

    /*
     * @InjectMocks creates object of
     * BusServiceImpl and injects mock dependencies.
     */
    @InjectMocks
    private BusServiceImpl busService;

    /*
     * Test data objects.
     */
    private BusRequestDTO requestDto;
    private AgencyOffice office;
    private Bus bus;

    /*
     * @BeforeEach runs before every test method.
     *
     * Used to initialize common test data.
     */
    @BeforeEach
    public void setUp() {

        /*
         * Creating request DTO object.
         */
        requestDto = new BusRequestDTO();

        requestDto.setOfficeId(20);
        requestDto.setRegistrationNumber("DL-1C-A1111");
        requestDto.setCapacity(40);
        requestDto.setType("Sleeper");

        /*
         * Creating office object.
         */
        office = new AgencyOffice();
        office.setId(20);

        /*
         * Creating Bus entity object.
         */
        bus = new Bus();

        bus.setId(80);
        bus.setOffice(office);
        bus.setRegistrationNumber("DL-1C-A1111");
        bus.setCapacity(40);
        bus.setType("Sleeper");
    }

    /*
     * 1. testCreateBus_Success
     *
     * Verify that bus creation works successfully
     * when valid details are provided.
     */
    @Test
    public void testCreateBus_Success() {

        /*
         * Mock officeRepo behavior.
         *
         * When findById(20) is called,
         * return office object.
         */
        when(officeRepo.findById(20))
                .thenReturn(Optional.of(office));

        /*
         * Mock busRepo save behavior.
         */
        when(busRepo.save(any(Bus.class)))
                .thenReturn(bus);

        /*
         * Call service method.
         */
        BusResponseDTO response =
                busService.createBus(requestDto);

        /*
         * Assertions verify expected output.
         */
        assertNotNull(response);

        assertEquals(80, response.getId());

        assertEquals(
                "DL-1C-A1111",
                response.getRegistrationNumber()
        );
    }

    /*
     * 2. testCreateBus_OfficeNotFound_ThrowsException
     *
     * Verify exception is thrown
     * when office does not exist.
     */
    @Test
    public void testCreateBus_OfficeNotFound_ThrowsException() {

        /*
         * Mock officeRepo to return empty.
         */
        when(officeRepo.findById(20))
                .thenReturn(Optional.empty());

        /*
         * Verify exception is thrown.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> busService.createBus(requestDto)
        );
    }

    /*
     * 3. testGetBusById_Success
     *
     * Verify bus retrieval by ID works correctly.
     */
    @Test
    public void testGetBusById_Success() {

        /*
         * Mock repository response.
         */
        when(busRepo.findById(80))
                .thenReturn(Optional.of(bus));

        /*
         * Call service method.
         */
        BusResponseDTO response =
                busService.getBusById(80);

        /*
         * Verify returned response.
         */
        assertNotNull(response);

        assertEquals(80, response.getId());
    }

    /*
     * 4. testGetBusById_NotFound_ThrowsException
     *
     * Verify exception is thrown
     * when bus ID does not exist.
     */
    @Test
    public void testGetBusById_NotFound_ThrowsException() {

        /*
         * Mock empty repository response.
         */
        when(busRepo.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify exception.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> busService.getBusById(999)
        );
    }

    /*
     * 5. testGetAllBuses_NotEmpty
     *
     * Verify fetching all buses works correctly.
     */
    @Test
    public void testGetAllBuses_NotEmpty() {

        /*
         * Mock repository response with list.
         */
        when(busRepo.findAll())
                .thenReturn(Arrays.asList(bus));

        /*
         * Call service method.
         */
        List<BusResponseDTO> response =
                busService.getAllBuses();

        /*
         * Verify list size.
         */
        assertEquals(1, response.size());
    }

    /*
     * 6. testGetAllBuses_Empty
     *
     * Verify empty list handling.
     */
    @Test
    public void testGetAllBuses_Empty() {

        /*
         * Mock empty list response.
         */
        when(busRepo.findAll())
                .thenReturn(Collections.emptyList());

        /*
         * Call service method.
         */
        List<BusResponseDTO> response =
                busService.getAllBuses();

        /*
         * Verify returned list is empty.
         */
        assertTrue(response.isEmpty());
    }

    /*
     * 7. testGetBusesByOffice_Success
     *
     * Verify buses are filtered correctly
     * using office ID.
     */
    @Test
    public void testGetBusesByOffice_Success() {

        /*
         * Mock repository response.
         */
        when(busRepo.findAll())
                .thenReturn(Arrays.asList(bus));

        /*
         * Call service method.
         */
        List<BusResponseDTO> response =
                busService.getBusesByOffice(20);

        /*
         * Verify filtered result size.
         */
        assertEquals(1, response.size());
    }

    /*
     * 8. testUpdateBus_Success
     *
     * Verify bus update works successfully.
     */
    @Test
    public void testUpdateBus_Success() {

        /*
         * Mock repository responses.
         */
        when(busRepo.findById(80))
                .thenReturn(Optional.of(bus));

        when(officeRepo.findById(20))
                .thenReturn(Optional.of(office));

        when(busRepo.save(any(Bus.class)))
                .thenReturn(bus);

        /*
         * Call update method.
         */
        BusResponseDTO response =
                busService.updateBus(80, requestDto);

        /*
         * Verify updated response.
         */
        assertNotNull(response);

        assertEquals(80, response.getId());
    }

    /*
     * 9. testUpdateBus_NotFound_ThrowsException
     *
     * Verify exception when updating missing bus.
     */
    @Test
    public void testUpdateBus_NotFound_ThrowsException() {

        /*
         * Mock empty response.
         */
        when(busRepo.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify exception.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> busService.updateBus(999, requestDto)
        );
    }

    /*
     * 10. testDeleteBus_Success
     *
     * Verify successful deletion of bus.
     */
    @Test
    public void testDeleteBus_Success() {

        /*
         * Mock repository response.
         */
        when(busRepo.findById(80))
                .thenReturn(Optional.of(bus));

        /*
         * doNothing() used for void methods.
         */
        doNothing().when(busRepo).delete(bus);

        /*
         * Verify no exception occurs.
         */
        assertAll(() -> busService.deleteBus(80));
    }

    /*
     * 11. testDeleteBus_NotFound_ThrowsException
     *
     * Verify exception when deleting missing bus.
     */
    @Test
    public void testDeleteBus_NotFound_ThrowsException() {

        /*
         * Mock empty repository response.
         */
        when(busRepo.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify exception.
         */
        assertThrows(
                ResourceNotFoundException.class,
                () -> busService.deleteBus(999)
        );
    }
}