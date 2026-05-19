package com.busticket.busticketbooking;

import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.entity.Booking;
import com.busticket.busticketbooking.entity.Bus;
import com.busticket.busticketbooking.entity.Customer;
import com.busticket.busticketbooking.entity.Driver;
import com.busticket.busticketbooking.entity.Payment;
import com.busticket.busticketbooking.entity.Review;
import com.busticket.busticketbooking.entity.Route;
import com.busticket.busticketbooking.entity.Trip;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.repo.BookingRepo;
import com.busticket.busticketbooking.repo.BusRepo;
import com.busticket.busticketbooking.repo.CustomerRepo;
import com.busticket.busticketbooking.repo.DriverRepo;
import com.busticket.busticketbooking.repo.PaymentRepo;
import com.busticket.busticketbooking.repo.ReviewRepo;
import com.busticket.busticketbooking.repo.RouteRepo;
import com.busticket.busticketbooking.repo.TripRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RepositoryTests {

    @Autowired private AddressRepo addressRepo;
    @Autowired private AgencyRepo agencyRepo;
    @Autowired private AgencyOfficeRepo agencyOfficeRepo;
    @Autowired private BookingRepo bookingRepo;
    @Autowired private BusRepo busRepo;
    @Autowired private CustomerRepo customerRepo;
    @Autowired private DriverRepo driverRepo;
    @Autowired private PaymentRepo paymentRepo;
    @Autowired private ReviewRepo reviewRepo;
    @Autowired private RouteRepo routeRepo;
    @Autowired private TripRepo tripRepo;

    @Test
    void saveAddressTest() {
        Address address = new Address();
        address.setAddress("12 Main Road");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setZipCode("600001");

        Address saved = addressRepo.save(address);

        assertNotNull(saved.getId());
        assertEquals("Chennai", saved.getCity());
    }

    @Test
    void saveAgencyTest() {
        Agency agency = new Agency();
        agency.setName("Sky Travels");
        agency.setContactPersonName("Ravi");
        agency.setEmail("skytravels@gmail.com");
        agency.setPhone("9876543210");

        Agency saved = agencyRepo.save(agency);

        assertNotNull(saved.getId());
        assertEquals("Sky Travels", saved.getName());
    }

    @Test
    void saveAgencyOfficeTest() {
        Address address = createAddress();
        Agency agency = createAgency();

        AgencyOffice office = new AgencyOffice();
        office.setAgency(agency);
        office.setAddress(address);
        office.setOfficeMail("office@skytravels.com");
        office.setOfficeContactPersonName("Office Person");
        office.setOfficeContactNumber("9999999999");

        AgencyOffice saved = agencyOfficeRepo.save(office);

        assertNotNull(saved.getId());
        assertEquals("office@skytravels.com", saved.getOfficeMail());
    }

    @Test
    void saveCustomerTest() {
        Address address = createAddress();

        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");
        customer.setAddress(address);

        Customer saved = customerRepo.save(customer);

        assertNotNull(saved.getId());
        assertEquals("Test User", saved.getName());
    }

    @Test
    void saveRouteTest() {
        Route route = new Route();
        route.setFromCity("Chennai");
        route.setToCity("Bangalore");
        route.setBreakPoints(2);
        route.setDuration(8);

        Route saved = routeRepo.save(route);

        assertNotNull(saved.getId());
        assertEquals("Chennai", saved.getFromCity());
    }

    @Test
    void saveTripTest() {
        Trip trip = createTrip();

        Trip saved = tripRepo.save(trip);

        assertNotNull(saved.getId());
        assertEquals("Chennai", saved.getRoute().getFromCity());
    }

    @Test
    void saveBookingTest() {
        Trip trip = createTrip();
        trip = tripRepo.save(trip);

        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setSeatNumber(12);
        booking.setStatus(Booking.BookingStatus.Available);

        Booking saved = bookingRepo.save(booking);

        assertNotNull(saved.getId());
        assertEquals(12, saved.getSeatNumber());
    }

    @Test
    void saveBusTest() {
        AgencyOffice office = createAgencyOffice();

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber("TN01AB1234");
        bus.setCapacity(40);
        bus.setType("AC");

        Bus saved = busRepo.save(bus);

        assertNotNull(saved.getId());
        assertEquals("TN01AB1234", saved.getRegistrationNumber());
    }

    @Test
    void saveDriverTest() {
        Address address = createAddress();
        AgencyOffice office = createAgencyOffice();

        Driver driver = new Driver();
        driver.setLicenseNumber("DL123456");
        driver.setName("Driver One");
        driver.setPhone("8888888888");
        driver.setOffice(office);
        driver.setAddress(address);

        Driver saved = driverRepo.save(driver);

        assertNotNull(saved.getId());
        assertEquals("Driver One", saved.getName());
    }

    @Test
    void findBusByOfficeIdTest() {
        AgencyOffice office = createAgencyOffice();

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber("TN02CD5678");
        bus.setCapacity(45);
        bus.setType("Sleeper");

        Bus savedBus = busRepo.save(bus);

        List<Bus> buses = busRepo.findByOffice_id(office.getId());

        assertFalse(buses.isEmpty());
        assertEquals(savedBus.getId(), buses.get(0).getId());
    }

    @Test
    void findDriverByOfficeIdTest() {
        Address address = createAddress();
        AgencyOffice office = createAgencyOffice();

        Driver driver = new Driver();
        driver.setLicenseNumber("DL654321");
        driver.setName("Driver Two");
        driver.setPhone("7777777777");
        driver.setOffice(office);
        driver.setAddress(address);

        Driver savedDriver = driverRepo.save(driver);

        List<Driver> drivers = driverRepo.findByOffice_id(office.getId());

        assertFalse(drivers.isEmpty());
        assertEquals(savedDriver.getId(), drivers.get(0).getId());
    }

    @Test
    void findPaymentByCustomerIdTest() {
        Customer customer = createCustomer();
        Booking booking = createBooking();

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setBooking(booking);
        payment.setAmount(BigDecimal.valueOf(500));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(Payment.PaymentStatus.Success);

        Payment saved = paymentRepo.save(payment);

        List<Payment> payments = paymentRepo.findByCustomerId(customer.getId());

        assertFalse(payments.isEmpty());
        assertEquals(saved.getId(), payments.get(0).getId());
    }

    @Test
    void findPaymentByBookingIdTest() {
        Customer customer = createCustomer();
        Booking booking = createBooking();

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setBooking(booking);
        payment.setAmount(BigDecimal.valueOf(700));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(Payment.PaymentStatus.Success);

        paymentRepo.save(payment);

        Optional<Payment> found = paymentRepo.findByBookingId(booking.getId());

        assertTrue(found.isPresent());
        assertEquals(booking.getId(), found.get().getBooking().getId());
    }

    @Test
    void findReviewByTripIdTest() {
        Customer customer = createCustomer();
        Trip trip = createTrip();
        trip = tripRepo.save(trip);

        Review review = new Review();
        review.setId(101);
        review.setCustomer(customer);
        review.setTrip(trip);
        review.setRating(5);
        review.setComment("Very good trip");
        review.setReviewDate(LocalDateTime.now());

        Review saved = reviewRepo.save(review);

        List<Review> reviews = reviewRepo.findByTripId(trip.getId());

        assertFalse(reviews.isEmpty());
        assertEquals(saved.getId(), reviews.get(0).getId());
    }

    @Test
    void findReviewByCustomerIdTest() {
        Customer customer = createCustomer();
        Trip trip = createTrip();
        trip = tripRepo.save(trip);

        Review review = new Review();
        review.setId(102);
        review.setCustomer(customer);
        review.setTrip(trip);
        review.setRating(4);
        review.setComment("Nice service");
        review.setReviewDate(LocalDateTime.now());

        Review saved = reviewRepo.save(review);

        List<Review> reviews = reviewRepo.findByCustomerId(customer.getId());

        assertFalse(reviews.isEmpty());
        assertEquals(saved.getId(), reviews.get(0).getId());
    }

    private Address createAddress() {
        Address address = new Address();
        address.setAddress("12 Main Road");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setZipCode("600001");
        return addressRepo.save(address);
    }

    private Agency createAgency() {
        Agency agency = new Agency();
        agency.setName("Sky Travels");
        agency.setContactPersonName("Ravi");
        agency.setEmail("skytravels@gmail.com");
        agency.setPhone("9876543210");
        return agencyRepo.save(agency);
    }

    private AgencyOffice createAgencyOffice() {
        Address address = createAddress();
        Agency agency = createAgency();

        AgencyOffice office = new AgencyOffice();
        office.setAgency(agency);
        office.setAddress(address);
        office.setOfficeMail("office@skytravels.com");
        office.setOfficeContactPersonName("Office Person");
        office.setOfficeContactNumber("9999999999");
        return agencyOfficeRepo.save(office);
    }

    private Customer createCustomer() {
        Address address = createAddress();

        Customer customer = new Customer();
        customer.setName("Test User");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");
        customer.setAddress(address);
        return customerRepo.save(customer);
    }

    private Route createRoute() {
        Route route = new Route();
        route.setFromCity("Chennai");
        route.setToCity("Bangalore");
        route.setBreakPoints(2);
        route.setDuration(8);
        return routeRepo.save(route);
    }

    private Bus createBus() {
        AgencyOffice office = createAgencyOffice();

        Bus bus = new Bus();
        bus.setOffice(office);
        bus.setRegistrationNumber("TN01AB1234");
        bus.setCapacity(40);
        bus.setType("AC");
        return busRepo.save(bus);
    }

    private Driver createDriver(String license, String name, String phone) {
        Address address = createAddress();
        AgencyOffice office = createAgencyOffice();

        Driver driver = new Driver();
        driver.setLicenseNumber(license);
        driver.setName(name);
        driver.setPhone(phone);
        driver.setOffice(office);
        driver.setAddress(address);
        return driverRepo.save(driver);
    }

    private Trip createTrip() {
        Route route = createRoute();
        Bus bus = createBus();
        Address boardingAddress = createAddress();
        Address droppingAddress = createAddress();
        Driver driver1 = createDriver("DL111111", "Driver One", "8888888888");
        Driver driver2 = createDriver("DL222222", "Driver Two", "7777777777");

        Trip trip = new Trip();
        trip.setRoute(route);
        trip.setBus(bus);
        trip.setBoardingAddress(boardingAddress);
        trip.setDroppingAddress(droppingAddress);
        trip.setDepartureTime(LocalDateTime.now().plusDays(1));
        trip.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(8));
        trip.setDriver1(driver1);
        trip.setDriver2(driver2);
        trip.setAvailableSeats(40);
        trip.setFare(BigDecimal.valueOf(750));
        trip.setTripDate(LocalDateTime.now().plusDays(1));

        return trip;
    }

    private Booking createBooking() {
        Trip trip = createTrip();
        trip = tripRepo.save(trip);

        Booking booking = new Booking();
        booking.setTrip(trip);
        booking.setSeatNumber(12);
        booking.setStatus(Booking.BookingStatus.Available);

        return bookingRepo.save(booking);
    }
}