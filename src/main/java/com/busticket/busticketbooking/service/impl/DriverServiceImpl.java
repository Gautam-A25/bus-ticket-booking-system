package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.service.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;

    public DriverServiceImpl(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public Driver registerDriver(Integer officeId, Driver driver) {

        return driverRepo.save(driver);
    }

    @Override
    public List<Driver> getDriversByOffice(Integer officeId) {

        return driverRepo.findByOffice_id(officeId);
    }

    @Override
    public Driver getDriverById(Integer driverId) {

        return driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver Not Found"));
    }

    @Override
    public Driver updateDriver(Integer driverId, Driver driver) {

        Driver existingDriver = getDriverById(driverId);

        existingDriver.setName(driver.getName());
        existingDriver.setPhone(driver.getPhone());
        existingDriver.setLicenseNumber(driver.getLicenseNumber());

        return driverRepo.save(existingDriver);
    }

    @Override
    public void deleteDriver(Integer driverId) {

        Driver driver = getDriverById(driverId);

        driverRepo.delete(driver);
    }
}