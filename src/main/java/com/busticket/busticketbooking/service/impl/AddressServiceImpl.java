package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.mapper.AddressMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.service.AddressService;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Customer;
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
 * Concrete implementation of {@link AddressService}.
 *
 * <p><b>Note on field injection:</b> The primary repository ({@code addressRepo}) uses
 * constructor injection (preferred). The cascade-delete repositories (driver, office,
 * customer, trip, booking, payment, review) use {@code @Autowired} field injection
 * because they are only needed for the transactional {@code deleteAddress} method
 * and were added incrementally.</p>
 *
 * <p><b>Cascade-delete strategy in {@code deleteAddress}:</b></p>
 * <ol>
 *   <li>Null-out the address FK on referencing {@code Driver}, {@code AgencyOffice},
 *       and {@code Customer} records (nullable FKs).</li>
 *   <li>For {@code Trip} records that use this address as boarding or dropping point
 *       (non-nullable FK), cascade-delete their bookings, payments, and reviews first,
 *       then delete the trip itself.</li>
 *   <li>Finally delete the address record.</li>
 * </ol>
 */
@Service
public class AddressServiceImpl implements AddressService {

        private final AddressRepo addressRepo;

        @Autowired
        private DriverRepo driverRepo;
        @Autowired
        private AgencyOfficeRepo officeRepo;
        @Autowired
        private CustomerRepo customerRepo;
        @Autowired
        private TripRepo tripRepo;
        @Autowired
        private BookingRepo bookingRepo;
        @Autowired
        private PaymentRepo paymentRepo;
        @Autowired
        private ReviewRepo reviewRepo;

    /**
     * Constructor injection for the primary repository.
     * Additional repos are field-injected via {@code @Autowired} above.
     */
    public AddressServiceImpl(AddressRepo addressRepo) {
            this.addressRepo = addressRepo;
    }

    /** Creates and persists a new address; maps from DTO to entity via {@link com.busticket.busticketbooking.mapper.AddressMapper}. */
    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO) {
            Address address = AddressMapper.toEntity(addressRequestDTO);
            Address savedAddress = addressRepo.save(address);
            return AddressMapper.toResponseDTO(savedAddress);
    }

    /** Fetches an address by ID; throws {@link ResourceNotFoundException} if not found. */
    @Override
    public AddressResponseDTO getAddressById(Integer id) {
            Address address = addressRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Address with ID " + id + " not found"));
            return AddressMapper.toResponseDTO(address);
    }

    /** Fetches all addresses and converts each entity to a response DTO via a stream. */
    @Override
    public List<AddressResponseDTO> getAllAddresses() {
            return addressRepo.findAll()
                            .stream()
                            .map(AddressMapper::toResponseDTO)
                            .collect(Collectors.toList());
    }

    /** Returns a paginated, ID-ascending page of addresses. */
    @Override
    public Page<AddressResponseDTO> getAddressPage(int page, int size) {
            return addressRepo.findAll(
                            PageRequest.of(page, size, Sort.by("id").ascending()))
                            .map(AddressMapper::toResponseDTO);
    }

    /** Updates all four fields of an existing address; throws 404 if not found. */
    @Override
    public AddressResponseDTO updateAddress(Integer id, AddressRequestDTO addressRequestDTO) {
            Address existingAddress = addressRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Address with ID " + id + " not found"));

            existingAddress.setAddress(addressRequestDTO.getAddress());
            existingAddress.setCity(addressRequestDTO.getCity());
            existingAddress.setState(addressRequestDTO.getState());
            existingAddress.setZipCode(addressRequestDTO.getZipCode());

            Address updatedAddress = addressRepo.save(existingAddress);
            return AddressMapper.toResponseDTO(updatedAddress);
    }

        @Override
        @Transactional
        public String deleteAddress(Integer id) {

                Address address = addressRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Address with ID " + id + " not found"));

                String addressDetails = "Address Deleted Successfully : \n" +
                                "ID = " + address.getId() + "\n" +
                                "Address = " + address.getAddress() + "\n" +
                                "City = " + address.getCity() + "\n" +
                                "State = " + address.getState() + "\n" +
                                "Zip Code = " + address.getZipCode();

                // 1. Find and update drivers referencing this address (nullable FK)
                List<Driver> drivers = driverRepo.findByAddressId(id);
                for (Driver driver : drivers) {
                        driver.setAddress(null);
                        driverRepo.save(driver);
                }

                // 2. Find and update agency offices referencing this address (nullable FK)
                List<AgencyOffice> offices = officeRepo.findByAddressId(id);
                for (AgencyOffice office : offices) {
                        office.setAddress(null);
                        officeRepo.save(office);
                }

                // 3. Find and update customers referencing this address (nullable FK)
                List<Customer> customers = customerRepo.findByAddressId(id);
                for (Customer customer : customers) {
                        customer.setAddress(null);
                        customerRepo.save(customer);
                }

                // 4. Find and delete trips referencing this address as boarding/dropping
                // (non-nullable FK)
                List<Trip> trips = tripRepo.findByBoardingAddressIdOrDroppingAddressId(id, id);
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

                addressRepo.delete(address);

                return addressDetails;
        }
}