package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver registerDriver(Integer officeId, Driver driver);

    List<Driver> getDriversByOffice(Integer officeId);

    Driver getDriverById(Integer driverId);

    Driver updateDriver(Integer driverId, Driver driver);

    void deleteDriver(Integer driverId);
}