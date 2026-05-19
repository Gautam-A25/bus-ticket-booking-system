package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.RouteDto.RouteRequestDto;
import com.busticket.busticketbooking.dto.RouteDto.RouteResponseDto;

import com.busticket.busticketbooking.entity.Route;

import com.busticket.busticketbooking.repo.RouteRepo;

import com.busticket.busticketbooking.service.impl.RouteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RouteServiceImplTest {

    @Mock
    private RouteRepo routeRepo;

    @InjectMocks
    private RouteServiceImpl routeService;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoutesTest() {

        Route route = new Route();

        route.setId(1);
        route.setFromCity("Chennai");
        route.setToCity("Bangalore");
        route.setBreakPoints(2);
        route.setDuration(8);

        when(routeRepo.findAll())
                .thenReturn(List.of(route));

        List<RouteResponseDto> routes =
                routeService.getAllRoutes();

        assertNotNull(routes);

        assertEquals(1, routes.size());

        assertEquals("Chennai",
                routes.get(0).getFromCity());
    }

    @Test
    void getRouteByIdTest() {

        Route route = new Route();

        route.setId(1);
        route.setFromCity("Delhi");
        route.setToCity("Mumbai");

        when(routeRepo.findById(1))
                .thenReturn(Optional.of(route));

        RouteResponseDto dto =
                routeService.getRouteById(1);

        assertNotNull(dto);

        assertEquals("Delhi",
                dto.getFromCity());
    }

    @Test
    void addRouteTest() {

        RouteRequestDto requestDto =
                new RouteRequestDto();

        requestDto.setFromCity("Hyderabad");
        requestDto.setToCity("Pune");
        requestDto.setBreakPoints(3);
        requestDto.setDuration(12);

        Route route = new Route();

        route.setId(1);
        route.setFromCity("Hyderabad");
        route.setToCity("Pune");
        route.setBreakPoints(3);
        route.setDuration(12);

        when(routeRepo.save(any(Route.class)))
                .thenReturn(route);

        RouteResponseDto dto =
                routeService.addRoute(requestDto);

        assertNotNull(dto);

        assertEquals("Hyderabad",
                dto.getFromCity());
    }

@Test
void deleteRouteTest() {

    Route route = new Route();

    route.setId(1);

    when(routeRepo.findById(1))
            .thenReturn(Optional.of(route));

    doNothing().when(routeRepo)
            .delete(route);

    routeService.deleteRoute(1);

    verify(routeRepo, times(1))
            .delete(route);
}
}