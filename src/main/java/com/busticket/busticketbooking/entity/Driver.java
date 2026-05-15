package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer id;

    @Column(name = "license_number", nullable = false, length = 20)
    private String licenseNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private AgencyOffice office;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Driver() {
    }

    public Driver(Integer id, String licenseNumber, String name, String phone, AgencyOffice office, Address address) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.phone = phone;
        this.office = office;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AgencyOffice getOffice() {
        return office;
    }

    public void setOffice(AgencyOffice office) {
        this.office = office;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id != null && id.equals(driver.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", officeId=" + (office != null ? office.getId() : null) +
                ", addressId=" + (address != null ? address.getId() : null) +
                '}';
    }
}