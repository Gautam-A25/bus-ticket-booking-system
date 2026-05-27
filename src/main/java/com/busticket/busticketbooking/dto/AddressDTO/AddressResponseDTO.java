package com.busticket.busticketbooking.dto.AddressDTO;

/**
 * Data Transfer Object representing a response for an Address.
 * Used to send detailed address information back to the client.
 */
public class AddressResponseDTO {

    /** The unique identifier of the address. */
    private Integer addressId;

    /** The street address details. */
    private String address;

    /** The city name. */
    private String city;

    /** The state name. */
    private String state;

    /** The postal zip code. */
    private String zipCode;

    /**
     * Default no-argument constructor.
     */
    public AddressResponseDTO() {
    }

    /**
     * Parameterized constructor to fully initialize the DTO.
     *
     * @param addressId the unique ID of the address
     * @param address   the street address
     * @param city      the city
     * @param state     the state
     * @param zipCode   the postal zip code
     */
    public AddressResponseDTO(Integer addressId, String address, String city, String state, String zipCode) {
        this.addressId = addressId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
}