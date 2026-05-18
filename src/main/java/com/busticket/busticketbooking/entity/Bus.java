package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;

    @NotNull(message = "Office is required")
    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private AgencyOffice office;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\- ]{3,20}$", message = "Registration number format is invalid")
    @Column(name = "registration_number", nullable = false, length = 20)
    private String registrationNumber;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    @Column(nullable = false)
    private Integer capacity;

    @NotBlank(message = "Bus type is required")
    @Size(max = 30, message = "Bus type must not exceed 30 characters")
    @Column(nullable = false, length = 30)
    private String type;

    public Bus() {
    }

    public Bus(Integer id, AgencyOffice office, String registrationNumber, Integer capacity, String type) {
        this.id = id;
        this.office = office;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AgencyOffice getOffice() {
        return office;
    }

    public void setOffice(AgencyOffice office) {
        this.office = office;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return id != null && id.equals(bus.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", officeId=" + (office != null ? office.getId() : null) +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                '}';
    }
}