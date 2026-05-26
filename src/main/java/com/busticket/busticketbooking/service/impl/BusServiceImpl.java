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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link BusService}.
 *
 * <p>A bus is always associated with an {@code AgencyOffice}.
 * The {@code createBus} and {@code updateBus} methods look up the office by ID
 * before persisting changes to ensure referential integrity.</p>
 *
 * <p><b>Cascade-delete strategy in {@code deleteBus}:</b> All trips using this bus
 * are located first; their bookings and payments are deleted, then their reviews,
 * then the trips themselves. Finally the bus record is deleted.
 * The method is annotated {@code @Transactional} to run as a single atomic unit.</p>
 */
@Service
public class BusServiceImpl implements BusService {

    /** Repository for Bus table — primary persistence target. */
    private final BusRepo busRepo;

    /** Repository for AgencyOffice table — required to validate the office FK. */
    private final AgencyOfficeRepo officeRepo;

    // Additional repos used only for cascade deletion; field-injected via @Autowired
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    /** Constructor injection for the two primary repositories. */
    public BusServiceImpl(BusRepo busRepo,
                          AgencyOfficeRepo officeRepo) {

        this.busRepo = busRepo;
        this.officeRepo = officeRepo;
    }

    /** Creates and saves a new bus entity from the request DTO, linking it to the resolved office. */
    @Override
    public BusResponseDTO createBus(BusRequestDTO dto) {

        // Resolve the office — throws 404 if the officeId doesn't match any record
        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        // Build the Bus entity and set all required fields
        Bus bus = new Bus();

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        // Persist and convert entity → response DTO
        Bus savedBus = busRepo.save(bus);
        return BusMapper.mapToResponseDto(savedBus);
    }

    /** Returns all buses in the system, mapping each entity to a response DTO. */
    @Override
    public List<BusResponseDTO> getAllBuses() {
        return busRepo.findAll()
                .stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /** Fetches a single bus by ID; throws {@link ResourceNotFoundException} if not found. */
    @Override
    public BusResponseDTO getBusById(Integer id) {
        Bus bus = busRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + id + " not found"
                        ));
        return BusMapper.mapToResponseDto(bus);
    }

    /** Returns all buses for a given office by filtering the full bus list by officeId. */
    @Override
    public List<BusResponseDTO> getBusesByOffice(Integer officeId) {
        List<Bus> buses = busRepo.findAll()
                .stream()
                // Keep only buses whose office matches the requested ID
                .filter(bus ->
                        bus.getOffice() != null &&
                        bus.getOffice().getId().equals(officeId))
                .collect(Collectors.toList());

        return buses.stream()
                .map(BusMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /** Updates office, registration number, capacity, and type of an existing bus. */
    @Override
    public BusResponseDTO updateBus(Integer busId,
                                    BusRequestDTO dto) {
        Bus bus = busRepo.findById(busId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bus with ID " + busId + " not found"
                        ));

        AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Office not found"));

        bus.setOffice(office);
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setType(dto.getType());

        Bus updatedBus = busRepo.save(bus);
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

    /** Returns a paginated slice of all buses (default sort order). */
    @Override
    public Page<BusResponseDTO> getBusPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busPage = busRepo.findAll(pageable);
        return busPage.map(BusMapper::mapToResponseDto);
    }
}