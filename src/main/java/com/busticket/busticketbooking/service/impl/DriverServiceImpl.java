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

/*
 * @Service tells Spring Boot that this class
 * contains business logic.
 *
 * Spring automatically creates Bean object
 * for this service class.
 */
@Service
public class DriverServiceImpl implements DriverService {

    /*
     * Repository object used to perform
     * database operations on Driver table.
     */
    private final DriverRepo driverRepo;

    /*
     * Repository object used to access
     * AgencyOffice table.
     */
    private final AgencyOfficeRepo officeRepo;

    /*
     * Repository object used to access
     * Address table.
     */
    private final AddressRepo addressRepo;

    /*
     * Constructor Injection.
     *
     * Spring automatically injects required
     * repository dependencies here.
     */
    public DriverServiceImpl(
            DriverRepo driverRepo,
            AgencyOfficeRepo officeRepo,
            AddressRepo addressRepo
    ) {
        this.driverRepo = driverRepo;
        this.officeRepo = officeRepo;
        this.addressRepo = addressRepo;
    }

    /*
     * Creates and saves new Driver data.
     */
    @Override
    public DriverResponseDTO createDriver(DriverRequestDTO dto) {

        /*
         * Check if driver with same license number
         * already exists in database.
         *
         * If exists, throw duplicate exception.
         */
        if (driverRepo.existsByLicenseNumber(dto.getLicenseNumber())) {

            throw new DuplicateResourceException(
                    "Driver with license number "
                            + dto.getLicenseNumber()
                            + " already exists"
            );
        }

        /*
         * Fetch office from database using office ID.
         *
         * If office not found,
         * throw custom exception.
         */
        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        /*
         * Initially address is null.
         */
        Address address = null;

        /*
         * If address ID is provided,
         * fetch address from database.
         */
        if (dto.getAddressId() != null) {

            address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Address not found"));
        }

        /*
         * Creating new Driver entity object.
         */
        Driver driver = new Driver();

        /*
         * Setting driver details.
         */
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());

        /*
         * Setting office relationship.
         */
        driver.setOffice(office);

        /*
         * Setting address relationship.
         */
        driver.setAddress(address);

        /*
         * Saving driver object into database.
         */
        Driver savedDriver = driverRepo.save(driver);

        /*
         * Convert Entity -> Response DTO
         * and return response.
         */
        return DriverMapper.mapToResponseDto(savedDriver);
    }

    /*
     * Fetches all drivers from database.
     */
    @Override
    public List<DriverResponseDTO> getAllDrivers() {

        /*
         * findAll() fetches all driver records.
         *
         * stream() processes list data.
         *
         * map() converts each Driver entity
         * into DriverResponseDTO.
         *
         * collect() converts stream back to list.
         */
        return driverRepo.findAll()
                .stream()
                .map(DriverMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /*
     * Fetches single driver using ID.
     */
    @Override
    public DriverResponseDTO getDriverById(Integer id) {

        /*
         * Find driver using ID.
         *
         * If driver not found,
         * throw ResourceNotFoundException.
         */
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver with ID " + id + " not found"
                        ));

        /*
         * Convert entity into response DTO.
         */
        return DriverMapper.mapToResponseDto(driver);
    }

    /*
     * Fetches all drivers belonging to specific office.
     */
    @Override
    public List<DriverResponseDTO> getDriversByOffice(Integer officeId) {

        /*
         * Fetch all drivers.
         *
         * Filter drivers based on office ID.
         */
        List<Driver> drivers = driverRepo.findAll()
                .stream()

                /*
                 * Keep only drivers whose office ID matches.
                 */
                .filter(driver ->
                        driver.getOffice() != null &&
                        driver.getOffice().getId().equals(officeId)
                )

                /*
                 * Convert filtered stream back into list.
                 */
                .collect(Collectors.toList());

        /*
         * Convert Driver entities into DTOs.
         */
        return drivers.stream()
                .map(DriverMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /*
     * Updates existing driver details.
     */
    @Override
    public DriverResponseDTO updateDriver(
            Integer driverId,
            DriverRequestDTO dto
    ) {

        /*
         * Find existing driver from database.
         */
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver with ID "
                                        + driverId
                                        + " not found"
                        ));

        /*
         * Fetch office using office ID.
         */
        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        /*
         * Initially address is null.
         */
        Address address = null;

        /*
         * If address ID exists,
         * fetch address from database.
         */
        if (dto.getAddressId() != null) {

            address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Address not found"));
        }

        /*
         * Updating driver details.
         */
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());

        /*
         * Updating office relationship.
         */
        driver.setOffice(office);

        /*
         * Updating address relationship.
         */
        driver.setAddress(address);

        /*
         * Save updated driver into database.
         */
        Driver updatedDriver = driverRepo.save(driver);

        /*
         * Convert updated entity into DTO.
         */
        return DriverMapper.mapToResponseDto(updatedDriver);
    }

    /*
     * Deletes driver using ID.
     */
    @Override
    public void deleteDriver(Integer id) {

        /*
         * Find driver from database.
         */
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver with ID "
                                        + id
                                        + " not found"
                        ));

        /*
         * Delete driver from database.
         */
        driverRepo.delete(driver);
    }
}