package com.busticket.busticketbooking.dto.ReportDTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class RevenueByAgencyResponseDTO {

    @NotNull(message = "Agency id is required")
    private Integer agencyId;

    @NotBlank(message = "Agency name is required")
    @Size(max = 255, message = "Agency name must not exceed 255 characters")
    private String agencyName;

    @NotNull(message = "Total revenue is required")
    @Positive(message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2,
            message = "Amount must have up to 8 integer digits and 2 decimal places")
    private BigDecimal totalRevenue;

    @NotNull(message = "Total bookings is required")
    private Long totalBookings;

    public RevenueByAgencyResponseDTO() {
    }

    public RevenueByAgencyResponseDTO(Integer agencyId,
                                      String agencyName,
                                      BigDecimal totalRevenue,
                                      Long totalBookings) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.totalRevenue = totalRevenue;
        this.totalBookings = totalBookings;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }
}