// Total tests: 9
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDto.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDto.RouteResponseDto;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.repo.RouteRepo;
import com.busticket.busticketbooking.service.impl.RouteServiceImpl;
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
public class RouteServiceImplTest {

    @Mock
    private RouteRepo routeRepo;

    @InjectMocks
    private RouteServiceImpl routeService;

    private RouteRequestDto requestDto;
    private Route route;

    @BeforeEach
    public void setUp() {
        requestDto = new RouteRequestDto();
        requestDto.setFromCity("Mumbai");
        requestDto.setToCity("Pune");
        requestDto.setBreakPoints(2);
        requestDto.setDuration(180);

        route = new Route();
        route.setId(56);
        route.setFromCity("Mumbai");
        route.setToCity("Pune");
        route.setBreakPoints(2);
        route.setDuration(180);
    }

    /**
     * testAddRoute_Success - Verify that a route is created successfully when valid details are supplied.
     */
    @Test
    public void testAddRoute_Success() {
        when(routeRepo.save(any(Route.class))).thenReturn(route);

        RouteResponseDto response = routeService.addRoute(requestDto);

        assertNotNull(response);
        assertEquals(56, response.getId());
        assertEquals("Mumbai", response.getFromCity());
    }

    /**
     * testGetRouteById_Success - Verify that a route is successfully retrieved by ID.
     */
    @Test
    public void testGetRouteById_Success() {
        when(routeRepo.findById(56)).thenReturn(Optional.of(route));
        RouteResponseDto response = routeService.getRouteById(56);
        assertNotNull(response);
        assertEquals(56, response.getId());
    }

    /**
     * testGetRouteById_NotFound_ThrowsException - Verify that requesting a missing ID throws RuntimeException.
     */
    @Test
    public void testGetRouteById_NotFound_ThrowsException() {
        when(routeRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> routeService.getRouteById(999));
    }

    /**
     * testGetAllRoutes_NotEmpty - Verify that list of routes is successfully retrieved.
     */
    @Test
    public void testGetAllRoutes_NotEmpty() {
        when(routeRepo.findAll()).thenReturn(Arrays.asList(route));
        List<RouteResponseDto> response = routeService.getAllRoutes();
        assertEquals(1, response.size());
    }

    /**
     * testGetAllRoutes_Empty - Verify that an empty list is handled properly.
     */
    @Test
    public void testGetAllRoutes_Empty() {
        when(routeRepo.findAll()).thenReturn(Collections.emptyList());
        List<RouteResponseDto> response = routeService.getAllRoutes();
        assertTrue(response.isEmpty());
    }

    /**
     * testUpdateRoute_Success - Verify that a route is updated successfully.
     */
    @Test
    public void testUpdateRoute_Success() {
        when(routeRepo.findById(56)).thenReturn(Optional.of(route));
        when(routeRepo.save(any(Route.class))).thenReturn(route);

        RouteResponseDto response = routeService.updateRoute(56, requestDto);
        assertNotNull(response);
        assertEquals(56, response.getId());
    }

    /**
     * testUpdateRoute_NotFound_ThrowsException - Verify that updating a missing ID throws RuntimeException.
     */
    @Test
    public void testUpdateRoute_NotFound_ThrowsException() {
        when(routeRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> routeService.updateRoute(999, requestDto));
    }

    /**
     * testDeleteRoute_Success - Verify that deleting an existing ID succeeds.
     */
    @Test
    public void testDeleteRoute_Success() {
        when(routeRepo.findById(56)).thenReturn(Optional.of(route));
        doNothing().when(routeRepo).delete(route);
        assertAll(() -> routeService.deleteRoute(56));
    }

    /**
     * testDeleteRoute_NotFound_ThrowsException - Verify that deleting a missing ID throws RuntimeException.
     */
    @Test
    public void testDeleteRoute_NotFound_ThrowsException() {
        when(routeRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> routeService.deleteRoute(999));
    }
}
