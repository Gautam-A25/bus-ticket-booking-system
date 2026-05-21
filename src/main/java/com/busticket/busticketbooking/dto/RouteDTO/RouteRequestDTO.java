package com.busticket.busticketbooking.dto.RouteDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
/*
 * DTO used for creating and updating routes.
 * Contains validation rules for route input data.
 */
public class RouteRequestDTO {
    /*
     * Source city name.
     * Cannot be empty.
     */
    @NotBlank(message = "From city is required")
    private String fromCity;
     /*
     * Destination city name.
     * Cannot be empty.
     */
    @NotBlank(message = "To city is required")
    private String toCity;
     /*
     * Number of break points in route.
     * Must be positive.
     */
    @NotNull(message = "Break points required")
    @Positive(message = "Break points must be positive")
    private Integer breakPoints;
    /*
     * Total route duration in hours.
     * Must be positive.
     */
    @NotNull(message = "Duration required")
    @Positive(message = "Duration must be positive")
    private Integer duration;
     // Getter for fromCity
    public String getFromCity() {
        return fromCity;
    }
    // Setter for fromCity
    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }
     // Getter for toCity
    public String getToCity() {
        return toCity;
    }
    // Setter for toCity
    public void setToCity(String toCity) {
        this.toCity = toCity;
    }
    // Getter for breakPoints
    public Integer getBreakPoints() {
        return breakPoints;
    }
     // Setter for breakPoints
    public void setBreakPoints(Integer breakPoints) {
        this.breakPoints = breakPoints;
    }
     // Getter for duration
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}