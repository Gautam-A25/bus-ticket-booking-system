package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;
import com.busticket.busticketbooking.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Controller layer handles report-related APIs
@RestController

// Base URL for report APIs
@RequestMapping("/api/v1/reports")
public class ReportController {

    // Service layer object for report business logic
    private final ReportService reportService;

    // Constructor injection for dependency injection
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Generates trip occupancy report for a specific date
    @GetMapping("/trips/occupancy")
    public List<TripOccupancyReportResponseDTO> getTripOccupancyReport(

            // Accepts trip date from request parameter
            @RequestParam

            // Converts request parameter into LocalDate format
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tripDate) {

        return reportService.getTripOccupancyReport(tripDate);
    }

    // Generates revenue report grouped by travel agency
    @GetMapping("/revenue/by-agency")
    public List<RevenueByAgencyResponseDTO> getRevenueByAgency(

            // Start date and time for revenue calculation
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            // End date and time for revenue calculation
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate) {

        return reportService.getRevenueByAgency(fromDate, toDate);
    }

    // Fetches top frequent customers based on booking count
    @GetMapping("/customers/frequent")
    public List<FrequentCustomerResponseDTO> getFrequentCustomers(

            // Limits number of customers returned
            @RequestParam Integer limit) {

        return reportService.getFrequentCustomers(limit);
    }
}