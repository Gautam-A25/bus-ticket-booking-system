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

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/trips/occupancy")
    public List<TripOccupancyReportResponseDTO> getTripOccupancyReport(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tripDate) {

        return reportService.getTripOccupancyReport(tripDate);
    }

    @GetMapping("/revenue/by-agency")
    public List<RevenueByAgencyResponseDTO> getRevenueByAgency(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate) {

        return reportService.getRevenueByAgency(fromDate, toDate);
    }

    @GetMapping("/customers/frequent")
    public List<FrequentCustomerResponseDTO> getFrequentCustomers(

            @RequestParam Integer limit) {

        return reportService.getFrequentCustomers(limit);
    }
}