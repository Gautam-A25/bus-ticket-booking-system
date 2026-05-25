package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.AgencyOfficeMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
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

@Service
public class AgencyOfficeServiceImpl implements AgencyOfficeService {

    private final AgencyOfficeRepo agencyOfficeRepo;
    private final AgencyRepo agencyRepo;
    private final AddressRepo addressRepo;

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

    public AgencyOfficeServiceImpl(AgencyOfficeRepo agencyOfficeRepo,
            AgencyRepo agencyRepo,
            AddressRepo addressRepo) {
        this.agencyOfficeRepo = agencyOfficeRepo;
        this.agencyRepo = agencyRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    public AgencyOfficeResponseDTO addAgencyOffice(Integer agencyId, AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        Integer agencyIdToUse = agencyId != null ? agencyId : agencyOfficeRequestDTO.getAgencyId();
        if (agencyIdToUse == null) {
            throw new ResourceNotFoundException("Agency ID is required");
        }

        Agency agency = agencyRepo.findById(agencyIdToUse)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + agencyIdToUse + " not found"));

        Address address = addressRepo.findById(agencyOfficeRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address with ID " + agencyOfficeRequestDTO.getAddressId() + " not found"));

        AgencyOffice agencyOffice = AgencyOfficeMapper.toEntity(agencyOfficeRequestDTO, agency, address);
        AgencyOffice savedAgencyOffice = agencyOfficeRepo.save(agencyOffice);

        return AgencyOfficeMapper.toResponseDTO(savedAgencyOffice);
    }

    @Override
    public AgencyOfficeResponseDTO getAgencyOfficeById(Integer id) {
        AgencyOffice agencyOffice = agencyOfficeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency Office with ID " + id + " not found"));
        return AgencyOfficeMapper.toResponseDTO(agencyOffice);
    }

    @Override
    public List<AgencyOfficeResponseDTO> getAgencyOfficesByAgencyId(Integer agencyId) {
        if (!agencyRepo.existsById(agencyId)) {
            throw new ResourceNotFoundException("Agency with ID " + agencyId + " not found");
        }

        return agencyOfficeRepo.findByAgency_Id(agencyId)
                .stream()
                .map(AgencyOfficeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AgencyOfficeResponseDTO> getAgencyOfficePage(int page, int size) {
        return agencyOfficeRepo.findAll(
                PageRequest.of(page, size, Sort.by("id").ascending()))
                .map(AgencyOfficeMapper::toResponseDTO);
    }

    @Override
    public AgencyOfficeResponseDTO updateAgencyOffice(Integer id, AgencyOfficeRequestDTO agencyOfficeRequestDTO) {
        AgencyOffice existingAgencyOffice = agencyOfficeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency Office with ID " + id + " not found"));

        Integer agencyIdToUse = agencyOfficeRequestDTO.getAgencyId() != null
                ? agencyOfficeRequestDTO.getAgencyId()
                : existingAgencyOffice.getAgency().getId();

        Agency agency = agencyRepo.findById(agencyIdToUse)
                .orElseThrow(() -> new ResourceNotFoundException("Agency with ID " + agencyIdToUse + " not found"));

        Address address = addressRepo.findById(agencyOfficeRequestDTO.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address with ID " + agencyOfficeRequestDTO.getAddressId() + " not found"));

        existingAgencyOffice.setAgency(agency);
        existingAgencyOffice.setOfficeMail(agencyOfficeRequestDTO.getOfficeMail());
        existingAgencyOffice.setOfficeContactPersonName(agencyOfficeRequestDTO.getOfficeContactPersonName());
        existingAgencyOffice.setOfficeContactNumber(agencyOfficeRequestDTO.getOfficeContactNumber());
        existingAgencyOffice.setAddress(address);

        AgencyOffice updatedAgencyOffice = agencyOfficeRepo.save(existingAgencyOffice);
        return AgencyOfficeMapper.toResponseDTO(updatedAgencyOffice);
    }

    @Override
    @Transactional
    public String deleteAgencyOffice(Integer id) {
        AgencyOffice office = agencyOfficeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Office with ID " + id + " not found"));

        String officeDetails = "Agency Office Deleted Successfully : \n" +
                "ID = " + office.getId() + "\n" +
                "Agency ID = " +
                (office.getAgency() != null
                        ? office.getAgency().getId()
                        : null)
                + "\n" +
                "Office Mail = " + office.getOfficeMail() + "\n" +
                "Office Contact Person Name = " + office.getOfficeContactPersonName() + "\n" +
                "Office Contact Number = " + office.getOfficeContactNumber() + "\n" +
                "Address ID = " +
                (office.getAddress() != null
                        ? office.getAddress().getId()
                        : null);

        // 1. Find and delete all drivers in this office
        List<Driver> drivers = driverRepo.findByOffice_Id(id);
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

        // 2. Find and delete all buses in this office
        List<Bus> buses = busRepo.findByOffice_Id(id);
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

        // 3. Delete office
        agencyOfficeRepo.delete(office);

        return officeDetails;
    }
}