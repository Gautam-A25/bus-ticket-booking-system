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

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepo;

    public ReportServiceImpl(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
    }

    @Override
    public List<TripOccupancyReportResponseDTO> getTripOccupancyReport(
            LocalDate tripDate) {

        return reportRepo.getTripOccupancyReport(tripDate);
    }

    @Override
    public List<RevenueByAgencyResponseDTO> getRevenueByAgency(
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        return reportRepo.getRevenueByAgency(fromDate, toDate);
    }

    @Override
    public List<FrequentCustomerResponseDTO> getFrequentCustomers(
            Integer limit) {

        return reportRepo.getFrequentCustomers()
                .stream()
                .limit(limit)
                .toList();
    }
}