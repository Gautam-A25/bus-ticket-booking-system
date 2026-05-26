package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface defining all report-related business operations.
 *
 * <p>Provides custom reports such as Trip Occupancy metrics, Revenue by Agency office,
 * and Top Frequent Customers to aid business analysis and management decision-making.</p>
 */
public interface ReportService {

    /**
     * Generates a trip occupancy report for a specific date.
     *
     * @param tripDate the date of the trips to generate the occupancy report for
     * @return a list of {@link TripOccupancyReportResponseDTO} containing trip capacity and booked seat details
     */
    List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate
    );

    /**
     * Generates a revenue report for agencies within a specific date range.
     *
     * @param fromDate the start date-time of the report range
     * @param toDate   the end date-time of the report range
     * @return a list of {@link RevenueByAgencyResponseDTO} containing total revenue collected by each agency
     */
    List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate
    );

    /**
     * Retrieves a list of the top frequent customers.
     *
     * @param limit the maximum number of records to retrieve
     * @return a list of {@link FrequentCustomerResponseDTO} representing customers with high booking frequencies
     */
    List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit
    );
}