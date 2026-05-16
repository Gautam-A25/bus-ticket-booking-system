package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "office_id", nullable = false)
    private AgencyOffice office;

    @Column(name = "registration_number", nullable = false, length = 20)
    private String registrationNumber;

    @Column(nullable = false)
    private Integer capacity;

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