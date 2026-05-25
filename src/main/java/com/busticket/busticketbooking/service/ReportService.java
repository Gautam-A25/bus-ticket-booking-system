package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Service interface defines report-related business operations
public interface ReportService {

    // Fetches trip occupancy report for a specific date
    List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate
    );

    // Fetches agency-wise revenue report within date range
    List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate
    );

    // Fetches top frequent customers based on limit value
    List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit
    );
}