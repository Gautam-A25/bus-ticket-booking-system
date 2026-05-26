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

/**
 * Concrete implementation of {@link ReportService}.
 *
 * <p>Handles execution of analytical queries to compile system-wide occupancy,
 * revenue, and frequent customer reports.</p>
 */
@Service
public class ReportServiceImpl implements ReportService {

    /** Repository for custom database report queries. */
    private final ReportRepo reportRepo;

    /**
     * Constructs a ReportServiceImpl with the required report repository.
     *
     * @param reportRepo repository for database report queries
     */
    public ReportServiceImpl(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate) {
        return reportRepo.getTripOccupancyReport(tripDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate) {
        return reportRepo.getRevenueByAgency(fromDate, toDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit) {
        return reportRepo.getFrequentCustomers()
                .stream()
                .limit(limit)
                .toList();
    }
}