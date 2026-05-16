package com.busticket.busticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Integer reviewId;
    private Integer customerId;
    private Integer tripId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewDate;
}
