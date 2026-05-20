package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.service.DriverService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.exception.DuplicateResourceException;
import com.busticket.busticketbooking.mapper.DriverMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final AgencyOfficeRepo officeRepo;
    private final AddressRepo addressRepo;

    public DriverServiceImpl(
            DriverRepo driverRepo,
            AgencyOfficeRepo officeRepo,
            AddressRepo addressRepo
    ) {
        this.driverRepo = driverRepo;
        this.officeRepo = officeRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    public DriverResponseDTO createDriver(DriverRequestDTO dto) {

        if (driverRepo.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new DuplicateResourceException("Driver with license number " + dto.getLicenseNumber() + " already exists");
        }

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        Address address = null;

        if (dto.getAddressId() != null) {
            address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        }

        Driver driver = new Driver();

        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setOffice(office);
        driver.setAddress(address);

        Driver savedDriver = driverRepo.save(driver);

        return DriverMapper.mapToResponseDto(savedDriver);
    }

    @Override
    public List<DriverResponseDTO> getAllDrivers() {

        return driverRepo.findAll()
                .stream()
                .map(DriverMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverResponseDTO getDriverById(Integer id) {

        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver with ID " + id + " not found"));

        return DriverMapper.mapToResponseDto(driver);
    }

    @Override
    public List<DriverResponseDTO> getDriversByOffice(Integer officeId) {

        List<Driver> drivers = driverRepo.findAll()
                .stream()
                .filter(driver ->
                        driver.getOffice() != null &&
                        driver.getOffice().getId().equals(officeId)
                )
                .collect(Collectors.toList());

        return drivers.stream()
                .map(DriverMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverResponseDTO updateDriver(Integer driverId, DriverRequestDTO dto) {

        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver with ID " + driverId + " not found"));

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        Address address = null;

        if (dto.getAddressId() != null) {
            address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        }

        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setOffice(office);
        driver.setAddress(address);

        Driver updatedDriver = driverRepo.save(driver);

        return DriverMapper.mapToResponseDto(updatedDriver);
    }

    @Override
    public void deleteDriver(Integer id) {

        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver with ID " + id + " not found"));

        driverRepo.delete(driver);
    }
}