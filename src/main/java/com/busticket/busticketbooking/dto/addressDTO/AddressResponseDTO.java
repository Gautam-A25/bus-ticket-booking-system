package com.busticket.busticketbooking.dto.addressDTO;

public class AddressResponseDTO {

    private Integer addressId;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public AddressResponseDTO() {
    }

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