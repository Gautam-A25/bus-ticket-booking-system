package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
/*
 * Entity class representing Route table.
 * Used to store route details in database.
 */
@Entity
/*
 * Maps this entity to "routes" table.
 */
@Table(name = "routes")
public class Route {
     /*
     * Primary key for route table.
     * Auto-generated using IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer id;
     /*
     * Starting city of route.
     * Cannot be empty.
     */
    @NotBlank(message = "From city is required")
    @Size(max = 255, message = "From city must not exceed 255 characters")
    @Column(name = "from_city", nullable = false, length = 255)
    private String fromCity;
     /*
     * Destination city of route.
     * Cannot be empty.
     */
    @NotBlank(message = "To city is required")
    @Size(max = 255, message = "To city must not exceed 255 characters")
    @Column(name = "to_city", nullable = false, length = 255)
    private String toCity;
    /*
     * Number of break points in route.
     * Cannot be negative.
     */
    @PositiveOrZero(message = "Break points cannot be negative")
    @Column(name = "break_points")
    private Integer breakPoints;
     /*
     * Total route duration.
     * Cannot be negative.
     */
    @PositiveOrZero(message = "Duration cannot be negative")
    @Column(name = "duration")
    private Integer duration;

    /** Default constructor required by JPA/Hibernate. */
    public Route() {
    }

    /** Parameterized constructor for building a Route from known values. */
    public Route(Integer id, String fromCity, String toCity, Integer breakPoints, Integer duration) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.breakPoints = breakPoints;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
    
    /** Compares two Route instances by ID only — safe for JPA-managed proxies. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id != null && id.equals(route.id);
    }
    /*
     * Generates hash code for entity.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    /*
     * Converts object data into readable string.
     */
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", breakPoints=" + breakPoints +
                ", duration=" + duration +
                '}';
    }
}