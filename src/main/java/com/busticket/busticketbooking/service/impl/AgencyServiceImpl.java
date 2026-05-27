package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.AgencyMapper;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.AgencyService;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@link AgencyService}.
 *
 * <p><b>Cascade-delete strategy in {@code deleteAgency}:</b></p>
 * <ol>
 *   <li>For each office of the agency:</li>
 *   <li>&nbsp;&nbsp;Delete each driver's trips (with their bookings, payments, reviews),
 *       then the driver.</li>
 *   <li>&nbsp;&nbsp;Delete each bus's trips (with their bookings, payments, reviews),
 *       then the bus.</li>
 *   <li>&nbsp;&nbsp;Delete the office itself.</li>
 *   <li>Finally delete the agency.</li>
 * </ol>
 *
 * <p>The entire delete is wrapped in a {@code @Transactional} boundary to ensure
 * atomicity — either everything is deleted or nothing is.</p>
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepo agencyRepo;

    @Autowired
    private AgencyOfficeRepo agencyOfficeRepo;
    @Autowired
    private DriverRepo driverRepo;
    @Autowired
    private BusRepo busRepo;
    @Autowired
    private TripRepo tripRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    /** Constructor injection for the primary agency repository. */
    public AgencyServiceImpl(AgencyRepo agencyRepo) {
        this.agencyRepo = agencyRepo;
    }

    /** Creates and persists a new agency; maps from DTO to entity via {@link com.busticket.busticketbooking.mapper.AgencyMapper}. */
    @Override
    public AgencyResponseDTO addAgency(AgencyRequestDTO agencyRequestDTO) {
        Agency agency = AgencyMapper.toEntity(agencyRequestDTO);
        Agency savedAgency = agencyRepo.save(agency);
        return AgencyMapper.toResponseDTO(savedAgency);
    }

    /** Fetches an agency by ID; throws {@link ResourceNotFoundException} if not found. */
    @Override
    public AgencyResponseDTO getAgencyById(Integer id) {
        Agency agency = agencyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + id + " not found"));
        return AgencyMapper.toResponseDTO(agency);
    }

    /** Fetches all agencies and converts each to a response DTO. */
    @Override
    public List<AgencyResponseDTO> getAllAgencies() {
        return agencyRepo.findAll()
                .stream()
                .map(AgencyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /** Returns a paginated, ID-ascending page of agencies. */
    @Override
    public Page<AgencyResponseDTO> getAgencyPage(int page, int size) {
        return agencyRepo.findAll(
                        PageRequest.of(page, size, Sort.by("id").ascending())
                )
                .map(AgencyMapper::toResponseDTO);
    }

    /** Updates name, contact person, email, and phone; throws 404 if not found. */
    @Override
    public AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO) {
        Agency existingAgency = agencyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + id + " not found"));

        existingAgency.setName(agencyRequestDTO.getName());
        existingAgency.setContactPersonName(agencyRequestDTO.getContactPersonName());
        existingAgency.setEmail(agencyRequestDTO.getEmail());
        existingAgency.setPhone(agencyRequestDTO.getPhone());

        Agency updatedAgency = agencyRepo.save(existingAgency);
        return AgencyMapper.toResponseDTO(updatedAgency);
    }

    @Override
    @Transactional
    public String deleteAgency(Integer id) {

        Agency agency = agencyRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Agency with ID " + id + " not found"
                        ));

        String agencyDetails =
                "Agency Deleted Successfully : \n" +
                        "ID = " + agency.getId() + "\n" +
                        "Name = " + agency.getName() + "\n" +
                        "Contact Person Name = " + agency.getContactPersonName() + "\n" +
                        "Email = " + agency.getEmail() + "\n" +
                        "Phone = " + agency.getPhone();

        // Find and delete all offices of this agency
        List<AgencyOffice> offices = agencyOfficeRepo.findByAgency_Id(id);
        for (AgencyOffice office : offices) {
            // Cascade delete drivers in office
            List<Driver> drivers = driverRepo.findByOffice_Id(office.getId());
            for (Driver driver : drivers) {
                // Cascade delete driver's trips
                List<Trip> trips = tripRepo.findByDriver1IdOrDriver2Id(driver.getId(), driver.getId());
                for (Trip trip : trips) {
                    // Cascade delete bookings and payments
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
                // Delete driver
                driverRepo.delete(driver);
            }

            // Cascade delete buses in office
            List<Bus> buses = busRepo.findByOffice_Id(office.getId());
            for (Bus bus : buses) {
                // Cascade delete bus's trips
                List<Trip> trips = tripRepo.findByBusId(bus.getId());
                for (Trip trip : trips) {
                    // Cascade delete bookings and payments
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
                // Delete bus
                busRepo.delete(bus);
            }

            // Delete office
            agencyOfficeRepo.delete(office);
        }

        agencyRepo.delete(agency);

        return agencyDetails;
    }
}