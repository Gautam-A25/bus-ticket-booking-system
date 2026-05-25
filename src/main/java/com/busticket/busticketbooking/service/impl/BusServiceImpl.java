package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.BusDTO.BusRequestDTO;
import com.busticket.busticketbooking.dto.BusDTO.BusResponseDTO;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.service.BusService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.BusMapper;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
public class BusServiceImpl implements BusService {

    /*
     * Repository object used to perform
     * database operations on Bus table.
     */
    private final BusRepo busRepo;

    /*
     * Repository object used to access
     * AgencyOffice table.
     */
    private final AgencyOfficeRepo officeRepo;

    /*
     * Constructor Injection.
     *
     * Spring automatically injects required
     * repository dependencies here.
     */
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    public BusServiceImpl(BusRepo busRepo,
                          AgencyOfficeRepo officeRepo) {

        this.busRepo = busRepo;
        this.officeRepo = officeRepo;
    }

    /*
     * Creates and saves new Bus data.
     */
    @Override
    public BusResponseDTO createBus(BusRequestDTO dto) {

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
         * Creating new Bus entity object.
         */
        Bus bus = new Bus();

        /*
         * Setting office relationship.
         */
        bus.setOffice(office);

        /*
         * Setting registration number.
         */
        bus.setRegistrationNumber(dto.getRegistrationNumber());

        /*
         * Setting seating capacity.
         */
        bus.setCapacity(dto.getCapacity());

        /*
         * Setting bus type.
         */
        bus.setType(dto.getType());

        /*
         * Saving bus object into database.
         */
        Bus savedBus = busRepo.save(bus);

        /*
         * Convert Entity -> Response DTO
         * and return response.
         */
        return BusMapper.mapToResponseDto(savedBus);
    }

    /*
     * Fetches all buses from database.
     */
    @Override
    public List<BusResponseDTO> getAllBuses() {

        /*
         * findAll() fetches all bus records.
         *
         * stream() processes list data.
         *
         * map() converts each Bus entity
         * into BusResponseDTO.
         *
         * collect() converts stream back to list.
         */
        return busRepo.findAll()
                .stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /*
     * Fetches single bus using ID.
     */
    @Override
    public BusResponseDTO getBusById(Integer id) {

        /*
         * Find bus using ID.
         *
         * If bus not found,
         * throw ResourceNotFoundException.
         */
        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + id + " not found"
                        ));

        /*
         * Convert entity into response DTO.
         */
        return BusMapper.mapToResponseDto(bus);
    }

    /*
     * Fetches all buses belonging to specific office.
     */
    @Override
    public List<BusResponseDTO> getBusesByOffice(Integer officeId) {

        /*
         * Fetch all buses.
         *
         * Filter buses based on office ID.
         */
        List<Bus> buses = busRepo.findAll()
                .stream()

                /*
                 * Keep only buses whose office ID matches.
                 */
                .filter(bus ->
                        bus.getOffice() != null &&
                        bus.getOffice().getId().equals(officeId))

                /*
                 * Convert filtered stream back into list.
                 */
                .collect(Collectors.toList());

        /*
         * Convert Bus entities into DTOs.
         */
        return buses.stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /*
     * Updates existing bus details.
     */
    @Override
    public BusResponseDTO updateBus(Integer busId,
                                    BusRequestDTO dto) {

        /*
         * Find existing bus from database.
         */
        Bus bus = busRepo.findById(busId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + busId + " not found"
                        ));

        /*
         * Fetch office using office ID.
         */
        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        /*
         * Updating bus details.
         */
        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        /*
         * Save updated data into database.
         */
        Bus updatedBus = busRepo.save(bus);

        /*
         * Convert updated entity into DTO.
         */
        return BusMapper.mapToResponseDto(updatedBus);
    }

    /*
     * Deletes bus using ID.
     */
    @Override
    @Transactional
    public String deleteBus(Integer id) {

        /*
         * Find bus from database.
         */
        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + id + " not found"
                        ));

        String busDetails =
                "Bus Deleted Successfully : \n" +
                        "ID = " + bus.getId() + "\n" +
                        "Office ID = " +
                        (bus.getOffice() != null
                                ? bus.getOffice().getId()
                                : null) + "\n" +
                        "Registration Number = " + bus.getRegistrationNumber() + "\n" +
                        "Capacity = " + bus.getCapacity() + "\n" +
                        "Type = " + bus.getType();

        // Find and delete trips referencing this bus
        List<Trip> trips = tripRepo.findByBusId(id);
        for (Trip trip : trips) {
            // Cascade delete bookings and payments first
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

        busRepo.delete(bus);

        return busDetails;
    }
}