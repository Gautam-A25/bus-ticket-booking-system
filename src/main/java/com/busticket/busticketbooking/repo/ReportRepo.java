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

import org.springframework.stereotype.Repository;

/**
 * Repository interface for executing analytical report queries.
 *
 * <p>Uses custom JPQL constructor queries to map raw join results directly into DTOs
 * for trip occupancy, agency revenue, and frequent customers.</p>
 */
@Repository
public interface ReportRepo extends JpaRepository<Booking, Integer> {

    /**
     * Generates a trip occupancy report for a specific date, calculating
     * capacity, booked seats, available seats, and booked occupancy percentages.
     *
     * @param tripDate the date of the trips
     * @return a list of {@link TripOccupancyReportResponseDTO} records
     */
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
            @Param("tripDate") LocalDate tripDate
    );

    /**
     * Generates an agency-wise revenue report within a given date range,
     * calculating total amount collected and total bookings.
     *
     * @param fromDate start date and time
     * @param toDate   end date and time
     * @return a list of {@link RevenueByAgencyResponseDTO} records ordered by revenue descending
     */
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
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    /**
     * Retrieves the top customers who have booked the most trips and spent the most money.
     *
     * @return a list of {@link FrequentCustomerResponseDTO} records ordered by booking count descending
     */
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