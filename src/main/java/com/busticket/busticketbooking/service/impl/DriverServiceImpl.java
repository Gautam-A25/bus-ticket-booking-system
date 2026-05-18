package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.DriverDto;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.service.DriverService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;

    public DriverServiceImpl(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public DriverDto registerDriver(Integer officeId, DriverDto driverDto) {

        Driver driver = new Driver();

        driver.setLicenseNumber(driverDto.getLicenseNumber());
        driver.setName(driverDto.getName());
        driver.setPhone(driverDto.getPhone());

        Driver savedDriver = driverRepo.save(driver);

        return mapToDto(savedDriver);
    }

    @Override
    public List<DriverDto> getDriversByOffice(Integer officeId) {

        return driverRepo.findByOffice_id(officeId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDto getDriverById(Integer driverId) {

        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver Not Found"));

        return mapToDto(driver);
    }

    @Override
    public DriverDto updateDriver(Integer driverId, DriverDto driverDto) {

        Driver existingDriver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver Not Found"));

        existingDriver.setLicenseNumber(driverDto.getLicenseNumber());
        existingDriver.setName(driverDto.getName());
        existingDriver.setPhone(driverDto.getPhone());

        Driver updatedDriver = driverRepo.save(existingDriver);

        return mapToDto(updatedDriver);
    }

    @Override
    public void deleteDriver(Integer driverId) {

        driverRepo.deleteById(driverId);
    }

    private DriverDto mapToDto(Driver driver) {

        DriverDto dto = new DriverDto();

        dto.setDriverId(driver.getId());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());

        return dto;
    }
}