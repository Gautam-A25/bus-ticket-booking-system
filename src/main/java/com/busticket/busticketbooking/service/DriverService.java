package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDto;

import java.util.List;

public interface DriverService {

    DriverDto registerDriver(Integer officeId, DriverDto driverDto);

    List<DriverDto> getDriversByOffice(Integer officeId);

    DriverDto getDriverById(Integer driverId);

    DriverDto updateDriver(Integer driverId, DriverDto driverDto);

    void deleteDriver(Integer driverId);
}