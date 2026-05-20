package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate
    );

    List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate
    );

    List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit
    );
}