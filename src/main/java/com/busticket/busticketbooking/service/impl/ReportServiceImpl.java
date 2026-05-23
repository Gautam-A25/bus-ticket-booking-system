package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;
import com.busticket.busticketbooking.repo.ReportRepo;
import com.busticket.busticketbooking.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Service layer handles report business logic
@Service
public class ReportServiceImpl implements ReportService {

    // Repository object for custom report queries
    private final ReportRepo reportRepo;

    // Constructor injection for dependency injection
    public ReportServiceImpl(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }

    // Fetches trip occupancy report for a specific date
    @Override
    public List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate) {

        return reportRepo.getTripOccupancyReport(tripDate);
    }

    // Fetches agency-wise revenue report within date range
    @Override
    public List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        return reportRepo.getRevenueByAgency(fromDate, toDate);
    }

    // Fetches top frequent customers based on limit value
    @Override
    public List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit) {

        return reportRepo.getFrequentCustomers()
                .stream()

                // Restricts result size based on limit parameter
                .limit(limit)

                .toList();
    }
}