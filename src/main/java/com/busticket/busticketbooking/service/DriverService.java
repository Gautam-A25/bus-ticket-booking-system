package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDto;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDto;

import java.util.List;

public interface DriverService {

    DriverResponseDto createDriver(DriverRequestDto dto);

    List<DriverResponseDto> getAllDrivers();

    DriverResponseDto getDriverById(Integer id);

    List<DriverResponseDto> getDriversByOffice(Integer officeId);

    DriverResponseDto updateDriver(Integer driverId, DriverRequestDto dto);

    void deleteDriver(Integer id);
}