package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer id;

    @Column(name = "from_city", nullable = false)
    private String fromCity;

    @Column(name = "to_city", nullable = false)
    private String toCity;

    @Column(name = "break_points")
    private Integer breakPoints;

    @Column(name = "duration")
    private Integer duration;

    public Route() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id != null && id.equals(route.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

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