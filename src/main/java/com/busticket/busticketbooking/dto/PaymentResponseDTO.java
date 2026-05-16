package com.busticket.busticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer bookingId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentStatus;
}
