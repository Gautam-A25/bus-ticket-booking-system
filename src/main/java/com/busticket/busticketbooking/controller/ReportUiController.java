package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;
import com.busticket.busticketbooking.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Web UI Controller that serves high-impact reports and analytical insights on the web front-end.
 *
 * <p>Serves dynamic occupancy maps, revenue aggregates by partner agency, and frequent customer lists
 * in a premium dashboard layout.</p>
 */
@Controller
@RequestMapping("/ui/reports")
public class ReportUiController {

    /** Service layer for report data calculations and multi-join lookups. */
    private final ReportService reportService;

    /**
     * Constructor injection for ReportService dependency.
     *
     * @param reportService the report service layer bean
     */
    public ReportUiController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String showReportsDashboard() {
        return "report/index";
    }

    @GetMapping("/trips/occupancy")
    public String getTripOccupancyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tripDate,
            Model model) {
        
        if (tripDate == null) {
            tripDate = LocalDate.now();
        }
        
        List<TripOccupancyReportResponseDTO> reportData = reportService.getTripOccupancyReport(tripDate);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("tripDate", tripDate);
        
        return "report/occupancy";
    }

    @GetMapping("/revenue/by-agency")
    public String getRevenueByAgencyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        
        if (fromDate == null) {
            fromDate = LocalDate.now().minusDays(30);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);
        
        List<RevenueByAgencyResponseDTO> reportData = reportService.getRevenueByAgency(fromDateTime, toDateTime);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        
        return "report/revenue";
    }

    @GetMapping("/customers/frequent")
    public String getFrequentCustomersReport(
            @RequestParam(defaultValue = "10") Integer limit,
            Model model) {
        
        List<FrequentCustomerResponseDTO> reportData = reportService.getFrequentCustomers(limit);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("limit", limit);
        
        return "report/frequent";
    }
}
