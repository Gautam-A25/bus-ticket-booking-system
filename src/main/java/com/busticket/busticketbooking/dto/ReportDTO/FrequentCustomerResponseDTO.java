package com.busticket.busticketbooking.dto.ReportDTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class FrequentCustomerResponseDTO {

    @NotNull(message = "Customer id is required")
    private Integer customerId;

    @NotBlank(message = "Customer name is required")
    @Size(max = 255, message = "Customer name must not exceed 255 characters")
    private String customerName;

    @NotNull(message = "Total bookings is required")
    private Long totalBookings;

    @NotNull(message = "Total spent is required")
    @Positive(message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2,
            message = "Amount must have up to 8 integer digits and 2 decimal places")
    private BigDecimal totalSpent;

    public FrequentCustomerResponseDTO() {
    }

    public FrequentCustomerResponseDTO(Integer customerId,
                                       String customerName,
                                       Long totalBookings,
                                       BigDecimal totalSpent) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalBookings = totalBookings;
        this.totalSpent = totalSpent;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }
}