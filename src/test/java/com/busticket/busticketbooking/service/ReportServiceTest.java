package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;
import com.busticket.busticketbooking.repo.ReportRepo;
import com.busticket.busticketbooking.service.impl.ReportServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepo reportRepo;

    @InjectMocks
    private ReportServiceImpl reportService;

    private RevenueByAgencyResponseDTO revenueReport;
    private FrequentCustomerResponseDTO customerReport;
    private TripOccupancyReportResponseDTO occupancyReport;

    private LocalDate tripDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @BeforeEach
    public void setUp() {

        tripDate = LocalDate.now();

        fromDate = LocalDateTime.now().minusDays(30);
        toDate = LocalDateTime.now();

        revenueReport = new RevenueByAgencyResponseDTO();
        revenueReport.setAgencyId(1);
        revenueReport.setAgencyName("KPN Travels");
        revenueReport.setTotalRevenue(BigDecimal.valueOf(25000));

        customerReport = new FrequentCustomerResponseDTO();
        customerReport.setCustomerId(10);
        customerReport.setCustomerName("Aman Sharma");
        customerReport.setTotalBookings(5L);

        occupancyReport = new TripOccupancyReportResponseDTO();
        occupancyReport.setTripId(11);
        occupancyReport.setBookedSeats(30L);
        occupancyReport.setOccupancyPercentage(75.0);
    }

    /**
     * 1. testGetTripOccupancyReport_Success
     * Verify occupancy report retrieval successfully.
     */
    @Test
    public void testGetTripOccupancyReport_Success() {

        when(reportRepo.getTripOccupancyReport(tripDate))
                .thenReturn(List.of(occupancyReport));

        List<TripOccupancyReportResponseDTO> response =
                reportService.getTripOccupancyReport(tripDate);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                75.0,
                response.get(0).getOccupancyPercentage()
        );
    }

    /**
     * 2. testGetTripOccupancyReport_Empty
     * Verify empty occupancy report handling.
     */
    @Test
    public void testGetTripOccupancyReport_Empty() {

        when(reportRepo.getTripOccupancyReport(tripDate))
                .thenReturn(Collections.emptyList());

        List<TripOccupancyReportResponseDTO> response =
                reportService.getTripOccupancyReport(tripDate);

        assertTrue(response.isEmpty());
    }

    /**
     * 3. testGetRevenueByAgency_Success
     * Verify revenue report retrieval successfully.
     */
    @Test
    public void testGetRevenueByAgency_Success() {

        when(reportRepo.getRevenueByAgency(fromDate, toDate))
                .thenReturn(List.of(revenueReport));

        List<RevenueByAgencyResponseDTO> response =
                reportService.getRevenueByAgency(fromDate, toDate);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                "KPN Travels",
                response.get(0).getAgencyName()
        );
    }

    /**
     * 4. testGetRevenueByAgency_Empty
     * Verify empty revenue report handling.
     */
    @Test
    public void testGetRevenueByAgency_Empty() {

        when(reportRepo.getRevenueByAgency(fromDate, toDate))
                .thenReturn(Collections.emptyList());

        List<RevenueByAgencyResponseDTO> response =
                reportService.getRevenueByAgency(fromDate, toDate);

        assertTrue(response.isEmpty());
    }

    /**
     * 5. testGetFrequentCustomers_Success
     * Verify frequent customer report retrieval.
     */
    @Test
    public void testGetFrequentCustomers_Success() {

        when(reportRepo.getFrequentCustomers())
                .thenReturn(List.of(customerReport));

        List<FrequentCustomerResponseDTO> response =
                reportService.getFrequentCustomers(5);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(
                "Aman Sharma",
                response.get(0).getCustomerName()
        );
    }

    /**
     * 6. testGetFrequentCustomers_Empty
     * Verify empty frequent customer report handling.
     */
    @Test
    public void testGetFrequentCustomers_Empty() {

        when(reportRepo.getFrequentCustomers())
                .thenReturn(Collections.emptyList());

        List<FrequentCustomerResponseDTO> response =
                reportService.getFrequentCustomers(5);

        assertTrue(response.isEmpty());
    }

    /**
     * 7. testRevenueAmountValidation
     * Verify total revenue amount is positive.
     */
    @Test
    public void testRevenueAmountValidation() {

        assertTrue(
                revenueReport.getTotalRevenue()
                        .compareTo(BigDecimal.ZERO) > 0
        );
    }

    /**
     * 8. testCustomerBookingCountValidation
     * Verify customer booking count is greater than zero.
     */
    @Test
    public void testCustomerBookingCountValidation() {

        assertTrue(
                customerReport.getTotalBookings() > 0
        );
    }

    /**
     * 9. testTripOccupancyPercentageValidation
     * Verify occupancy percentage is valid.
     */
    @Test
    public void testTripOccupancyPercentageValidation() {

        assertTrue(
                occupancyReport.getOccupancyPercentage() <= 100
        );
    }

    /**
     * 10. testTripOccupancyBookedSeatsValidation
     * Verify booked seats are positive.
     */
    @Test
    public void testTripOccupancyBookedSeatsValidation() {

        assertTrue(
                occupancyReport.getBookedSeats() > 0
        );
    }
}