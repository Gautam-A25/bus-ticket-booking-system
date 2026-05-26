package com.busticket.busticketbooking.service.impl;

/*
 * Importing Driver Request DTO
 * Used to receive Driver data from UI/API
 */
import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;

/*
 * Importing Driver Response DTO
 * Used to send Driver data back to client
 */
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;

/*
 * Importing Address Entity
 */
import com.busticket.busticketbooking.entity.Address;

/*
 * Importing AgencyOffice Entity
 */
import com.busticket.busticketbooking.entity.AgencyOffice;

/*
 * Importing Driver Entity
 */
import com.busticket.busticketbooking.entity.Driver;

/*
 * Repository for Address table
 */
import com.busticket.busticketbooking.repo.AddressRepo;

/*
 * Repository for AgencyOffice table
 */
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;

/*
 * Repository for Driver table
 */
import com.busticket.busticketbooking.repo.DriverRepo;

/*
 * Driver Service Interface
 */
import com.busticket.busticketbooking.service.DriverService;

/*
 * Custom Exception
 * Thrown when resource is not found
 */
import com.busticket.busticketbooking.exception.ResourceNotFoundException;

/*
 * Custom Exception
 * Thrown when duplicate resource exists
 */
import com.busticket.busticketbooking.exception.DuplicateResourceException;

/*
 * Mapper class used to convert Entity to DTO
 */
import com.busticket.busticketbooking.mapper.DriverMapper;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Service Implementation Class
 *
 * Implements DriverService interface
 */
@Service
public class DriverServiceImpl implements DriverService {

        /*
         * Repository object for Driver Entity
         */
        private final DriverRepo driverRepo;

        /*
         * Repository object for AgencyOffice Entity
         */
        private final AgencyOfficeRepo officeRepo;

        /*
         * Repository object for Address Entity
         */
        private final AddressRepo addressRepo;

        @Autowired
        private TripRepo tripRepo;
        @Autowired
        private BookingRepo bookingRepo;
        @Autowired
        private PaymentRepo paymentRepo;
        @Autowired
        private ReviewRepo reviewRepo;

        public DriverServiceImpl(
                        DriverRepo driverRepo,
                        AgencyOfficeRepo officeRepo,
                        AddressRepo addressRepo) {
                this.driverRepo = driverRepo;
                this.officeRepo = officeRepo;
                this.addressRepo = addressRepo;
        }

        /*
         * Create Driver Method
         *
         * Saves new driver into database
         */
        @Override
        public DriverResponseDTO createDriver(DriverRequestDTO dto) {

                /*
                 * Check if driver with same license number already exists
                 */
                if (driverRepo.existsByLicenseNumber(dto.getLicenseNumber())) {

                        throw new DuplicateResourceException(
                                        "Driver with license number "
                                                        + dto.getLicenseNumber()
                                                        + " already exists");
                }

                /*
                 * Fetch Office using office ID
                 */
                AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Office not found"));

                /*
                 * Initially address is null
                 */
                Address address = null;

                /*
                 * If Address ID is provided
                 * then fetch address from database
                 */
                if (dto.getAddressId() != null) {

                        address = addressRepo.findById(dto.getAddressId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Address not found"));
                }

                /*
                 * Create Driver Entity object
                 */
                Driver driver = new Driver();

                /*
                 * Set driver details
                 */
                driver.setLicenseNumber(dto.getLicenseNumber());
                driver.setName(dto.getName());
                driver.setPhone(dto.getPhone());
                driver.setOffice(office);
                driver.setAddress(address);

                /*
                 * Save Driver into database
                 */
                Driver savedDriver = driverRepo.save(driver);

                /*
                 * Convert Entity -> DTO
                 */
                return DriverMapper.mapToResponseDto(savedDriver);
        }

        /*
         * Fetch all Drivers
         */
        @Override
        public List<DriverResponseDTO> getAllDrivers() {

                /*
                 * Fetch all Drivers
                 * Convert Entity List -> DTO List
                 */
                return driverRepo.findAll()
                                .stream()
                                .map(DriverMapper::mapToResponseDto)
                                .collect(Collectors.toList());
        }

        /*
         * Fetch paginated Driver data
         */
        @Override
        public Page<DriverResponseDTO> getDriverPage(
                        int page,
                        int size) {

                /*
                 * Create Pageable object
                 */
                Pageable pageable = PageRequest.of(page, size);

                /*
                 * Fetch paginated Drivers
                 */
                return driverRepo.findAll(pageable)
                                .map(DriverMapper::mapToResponseDto);
        }

        @Override
        public DriverResponseDTO getDriverById(Integer id) {

                /*
                 * Find Driver by ID
                 */
                Driver driver = driverRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Driver with ID "
                                                                + id
                                                                + " not found"));

                /*
                 * Convert Entity -> DTO
                 */
                return DriverMapper.mapToResponseDto(driver);
        }

        /*
         * Fetch all Drivers belonging to a specific Office
         */
        @Override
        public List<DriverResponseDTO> getDriversByOffice(Integer officeId) {

                /*
                 * Fetch all drivers
                 * Filter by office ID
                 */
                List<Driver> drivers = driverRepo.findAll()
                                .stream()
                                .filter(driver -> driver.getOffice() != null &&
                                                driver.getOffice()
                                                                .getId()
                                                                .equals(officeId))
                                .collect(Collectors.toList());

                /*
                 * Convert Entity List -> DTO List
                 */
                return drivers.stream()
                                .map(DriverMapper::mapToResponseDto)
                                .collect(Collectors.toList());
        }

        /*
         * Update existing Driver
         */
        @Override
        public DriverResponseDTO updateDriver(
                        Integer driverId,
                        DriverRequestDTO dto) {

                /*
                 * Fetch existing Driver
                 */
                Driver driver = driverRepo.findById(driverId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Driver with ID "
                                                                + driverId
                                                                + " not found"));

                /*
                 * Fetch Office using Office ID
                 */
                AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Office not found"));

                /*
                 * Initially address is null
                 */
                Address address = null;

                /*
                 * Fetch Address if address ID exists
                 */
                if (dto.getAddressId() != null) {

                        address = addressRepo.findById(dto.getAddressId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Address not found"));
                }

                /*
                 * Update driver details
                 */
                driver.setLicenseNumber(dto.getLicenseNumber());
                driver.setName(dto.getName());
                driver.setPhone(dto.getPhone());
                driver.setOffice(office);
                driver.setAddress(address);

                /*
                 * Save updated Driver
                 */
                Driver updatedDriver = driverRepo.save(driver);

                /*
                 * Convert Entity -> DTO
                 */
                return DriverMapper.mapToResponseDto(updatedDriver);
        }

        /*
         * Delete Driver using Driver ID
         */
        @Override
        @Transactional
        public String deleteDriver(Integer id) {

                Driver driver = driverRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Driver with ID " + id + " not found"));

                // Find and delete trips where this driver is driver1 or driver2
                List<Trip> trips = tripRepo.findByDriver1IdOrDriver2Id(id, id);
                for (Trip trip : trips) {
                        // Cascade delete payments first
                        List<Booking> bookings = bookingRepo.findByTripId(trip.getId());
                        for (Booking booking : bookings) {
                                paymentRepo.findByBookingId(booking.getId()).ifPresent(paymentRepo::delete);
                        }
                        bookingRepo.deleteAll(bookings);

                        // Cascade delete reviews
                        List<Review> reviews = reviewRepo.findByTripId(trip.getId());
                        reviewRepo.deleteAll(reviews);

                        // Delete trip
                        tripRepo.delete(trip);
                }

                driverRepo.delete(driver);

                return "Driver deleted successfully";
        }
}