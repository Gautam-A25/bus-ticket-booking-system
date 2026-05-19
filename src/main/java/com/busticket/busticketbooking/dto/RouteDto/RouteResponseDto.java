package com.busticket.busticketbooking.dto.RouteDto;

public class RouteResponseDto {

    private Integer id;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;

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
}