package com.busticket.busticketbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing the {@code addresses} table.
 *
 * <p>A shared address record that can be referenced by multiple entities:
 * {@link Customer}, {@link Driver}, and {@link Trip} (for boarding/dropping locations).
 * Reusing address records avoids duplication of location data.</p>
 */
@Entity
@Table(name = "addresses")
public class Address {

    /** Auto-generated primary key for the address record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    /** Street-level address line (e.g., "123 Main St"); required, max 255 chars. */
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String address;

    /** City name; required, max 255 chars. */
    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters and valid name characters"
    )
    private String city;

    /** State / province name; required, max 255 chars. */
    @NotBlank(message = "State is required")
    @Size(max = 255, message = "State must not exceed 255 characters")
    @Column(nullable = false, length = 255)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z\\s.'-]*$",
            message = "Must contain only letters and valid name characters"
    )
    private String state;

    /** Postal / ZIP code; required, max 10 chars. */
    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Zip code must contain exactly 6 digits")
    @Size(max = 6, message = "Zip code must not exceed 6 characters")
    @Column(name = "zip_code", nullable = false, length = 6)
    private String zipCode;

    public Address() {
    }

    public Address(Integer id, String address, String city, String state, String zipCode) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /** Compares two Address instances by ID only — safe for JPA-managed proxies. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return id != null && id.equals(address1.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}