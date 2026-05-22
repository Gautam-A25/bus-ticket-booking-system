package com.busticket.busticketbooking.repo;

import com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO;
import com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO;
import com.busticket.busticketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Repository layer handles custom report queries
public interface ReportRepo extends JpaRepository<Booking, Integer> {

    // Generates occupancy report for trips on a specific date
    @Query("""
            SELECT new com.busticket.busticketbooking.dto.ReportDTO.TripOccupancyReportResponseDTO(
                t.id,
                CONCAT(r.fromCity, ' - ', r.toCity),
                bus.capacity,
                COUNT(b.id),
                CAST((bus.capacity - COUNT(b.id)) AS long),
                CAST(((COUNT(b.id) * 100.0) / bus.capacity) AS double)
            )
            FROM Trip t
            JOIN t.route r
            JOIN t.bus bus
            LEFT JOIN Booking b ON b.trip.id = t.id
            WHERE DATE(t.tripDate) = :tripDate
            GROUP BY t.id, r.fromCity, r.toCity, bus.capacity
            """)
    List<TripOccupancyReportResponseDTO> getTripOccupancyReport(

            // Accepts trip date parameter for filtering
            @Param("tripDate") LocalDate tripDate
    );



    // Generates agency-wise revenue report within given date range
    @Query("""
            SELECT new com.busticket.busticketbooking.dto.ReportDTO.RevenueByAgencyResponseDTO(
                a.id,
                a.name,
                SUM(p.amount),
                COUNT(b.id)
            )
            FROM Payment p
            JOIN p.booking b
            JOIN b.trip t
            JOIN t.bus bus
            JOIN bus.office office
            JOIN office.agency a
            WHERE p.paymentDate BETWEEN :fromDate AND :toDate
            GROUP BY a.id, a.name
            ORDER BY SUM(p.amount) DESC
            """)
    List<RevenueByAgencyResponseDTO> getRevenueByAgency(

            // Start date and time for revenue filtering
            @Param("fromDate") LocalDateTime fromDate,

            // End date and time for revenue filtering
            @Param("toDate") LocalDateTime toDate
    );



    // Fetches customers with highest booking count
    @Query("""
            SELECT new com.busticket.busticketbooking.dto.ReportDTO.FrequentCustomerResponseDTO(
                c.id,
                c.name,
                COUNT(b.id),
                SUM(p.amount)
            )
            FROM Payment p
            JOIN p.customer c
            JOIN p.booking b
            GROUP BY c.id, c.name
            ORDER BY COUNT(b.id) DESC
            """)
    List<FrequentCustomerResponseDTO> getFrequentCustomers();
}