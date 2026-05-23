package com.busticket.busticketbooking.dto.ReportDTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

// DTO used to send frequent customer report data
public class FrequentCustomerResponseDTO {

    // Stores unique customer ID
    @NotNull(message = "Customer id is required")
    private Integer customerId;

    // Stores customer name
    @NotBlank(message = "Customer name is required")

    // Limits customer name length to 255 characters
    @Size(max = 255, message = "Customer name must not exceed 255 characters")
    private String customerName;

    // Stores total number of bookings made by customer
    @NotNull(message = "Total bookings is required")
    private Long totalBookings;

    // Stores total amount spent by customer
    @NotNull(message = "Total spent is required")

    // Ensures amount is greater than 0
    @Positive(message = "Amount must be greater than 0")

    // Restricts decimal format for amount value
    @Digits(integer = 8, fraction = 2,
            message = "Amount must have up to 8 integer digits and 2 decimal places")
    private BigDecimal totalSpent;

    // Default constructor
    public FrequentCustomerResponseDTO() {
    }

    // Parameterized constructor for object initialization
    public FrequentCustomerResponseDTO(Integer customerId,
                                       String customerName,
                                       Long totalBookings,
                                       BigDecimal totalSpent) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalBookings = totalBookings;
        this.totalSpent = totalSpent;
    }

    // Returns customer ID
    public Integer getCustomerId() {
        return customerId;
    }

    // Sets customer ID
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    // Returns customer name
    public String getCustomerName() {
        return customerName;
    }

    // Sets customer name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Returns total booking count
    public Long getTotalBookings() {
        return totalBookings;
    }

    // Sets total booking count
    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    // Returns total amount spent
    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    // Sets total amount spent
    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }
}