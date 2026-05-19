package com.busticket.busticketbooking.dto.RouteDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RouteRequestDto {

    @NotBlank(message = "From city is required")
    private String fromCity;

    @NotBlank(message = "To city is required")
    private String toCity;

    @NotNull(message = "Break points required")
    @Positive(message = "Break points must be positive")
    private Integer breakPoints;

    @NotNull(message = "Duration required")
    @Positive(message = "Duration must be positive")
    private Integer duration;

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Integer getBreakPoints() {
        return breakPoints;
    }

    public void setBreakPoints(Integer breakPoints) {
        this.breakPoints = breakPoints;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}