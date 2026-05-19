// Total tests: 11
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.BusDto.BusRequestDto;
import com.busticket.busticketbooking.dto.BusDto.BusResponseDto;
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

@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    @Mock
    private BusRepo busRepo;

    @Mock
    private AgencyOfficeRepo officeRepo;

    @InjectMocks
    private BusServiceImpl busService;

    private BusRequestDto requestDto;
    private AgencyOffice office;
    private Bus bus;

    @BeforeEach
    public void setUp() {
        requestDto = new BusRequestDto();
        requestDto.setOfficeId(20);
        requestDto.setRegistrationNumber("DL-1C-A1111");
        requestDto.setCapacity(40);
        requestDto.setType("Sleeper");

        office = new AgencyOffice();
        office.setId(20);

        bus = new Bus();
        bus.setId(80);
        bus.setOffice(office);
        bus.setRegistrationNumber("DL-1C-A1111");
        bus.setCapacity(40);
        bus.setType("Sleeper");
    }

    /**
     * 1. testCreateBus_Success - Verify that a bus is created successfully when valid details are supplied.
     */
    @Test
    public void testCreateBus_Success() {
        when(officeRepo.findById(20)).thenReturn(Optional.of(office));
        when(busRepo.save(any(Bus.class))).thenReturn(bus);

        BusResponseDto response = busService.createBus(requestDto);

        assertNotNull(response);
        assertEquals(80, response.getId());
        assertEquals("DL-1C-A1111", response.getRegistrationNumber());
    }

    /**
     * 2. testCreateBus_OfficeNotFound_ThrowsException - Verify that creating a bus for a missing office throws ResourceNotFoundException.
     */
    @Test
    public void testCreateBus_OfficeNotFound_ThrowsException() {
        when(officeRepo.findById(20)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> busService.createBus(requestDto));
    }

    /**
     * 3. testGetBusById_Success - Verify that a bus is successfully retrieved by ID.
     */
    @Test
    public void testGetBusById_Success() {
        when(busRepo.findById(80)).thenReturn(Optional.of(bus));
        BusResponseDto response = busService.getBusById(80);
        assertNotNull(response);
        assertEquals(80, response.getId());
    }

    /**
     * 4. testGetBusById_NotFound_ThrowsException - Verify that requesting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetBusById_NotFound_ThrowsException() {
        when(busRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> busService.getBusById(999));
    }

    /**
     * 5. testGetAllBuses_NotEmpty - Verify that list of buses is successfully retrieved.
     */
    @Test
    public void testGetAllBuses_NotEmpty() {
        when(busRepo.findAll()).thenReturn(Arrays.asList(bus));
        List<BusResponseDto> response = busService.getAllBuses();
        assertEquals(1, response.size());
    }

    /**
     * 6. testGetAllBuses_Empty - Verify that an empty list is handled properly.
     */
    @Test
    public void testGetAllBuses_Empty() {
        when(busRepo.findAll()).thenReturn(Collections.emptyList());
        List<BusResponseDto> response = busService.getAllBuses();
        assertTrue(response.isEmpty());
    }

    /**
     * 7. testGetBusesByOffice_Success - Verify that buses are retrieved correctly based on their office ID filter.
     */
    @Test
    public void testGetBusesByOffice_Success() {
        when(busRepo.findAll()).thenReturn(Arrays.asList(bus));
        List<BusResponseDto> response = busService.getBusesByOffice(20);
        assertEquals(1, response.size());
    }

    /**
     * 8. testUpdateBus_Success - Verify that a bus is updated successfully.
     */
    @Test
    public void testUpdateBus_Success() {
        when(busRepo.findById(80)).thenReturn(Optional.of(bus));
        when(officeRepo.findById(20)).thenReturn(Optional.of(office));
        when(busRepo.save(any(Bus.class))).thenReturn(bus);

        BusResponseDto response = busService.updateBus(80, requestDto);
        assertNotNull(response);
        assertEquals(80, response.getId());
    }

    /**
     * 9. testUpdateBus_NotFound_ThrowsException - Verify that updating a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testUpdateBus_NotFound_ThrowsException() {
        when(busRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> busService.updateBus(999, requestDto));
    }

    /**
     * 10. testDeleteBus_Success - Verify that deleting an existing ID succeeds.
     */
    @Test
    public void testDeleteBus_Success() {
        when(busRepo.findById(80)).thenReturn(Optional.of(bus));
        doNothing().when(busRepo).delete(bus);
        assertAll(() -> busService.deleteBus(80));
    }

    /**
     * 11. testDeleteBus_NotFound_ThrowsException - Verify that deleting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testDeleteBus_NotFound_ThrowsException() {
        when(busRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> busService.deleteBus(999));
    }
}
