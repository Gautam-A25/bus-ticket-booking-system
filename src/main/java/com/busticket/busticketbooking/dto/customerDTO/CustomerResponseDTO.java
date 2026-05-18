package com.busticket.busticketbooking.dto.customerDTO;

// DTO used for customer response data
public class CustomerResponseDTO {

    // Customer ID
    private Integer id;

    // Customer name
    private String name;

    // Customer email
    private String email;

    // Customer phone number
    private String phone;

    // Address ID associated with customer
    private Integer addressId;

    // Default constructor
    public CustomerResponseDTO() {
    }

    // Parameterized constructor
    public CustomerResponseDTO(
            Integer id,
            String name,
            String email,
            String phone,
            Integer addressId
    ) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressId = addressId;
    }

    // Getter for customer ID
    public Integer getId() {
        return id;
    }

    // Setter for customer ID
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter for customer name
    public String getName() {
        return name;
    }

    // Setter for customer name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for customer email
    public String getEmail() {
        return email;
    }

    // Setter for customer email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for customer phone number
    public String getPhone() {
        return phone;
    }

    // Setter for customer phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter for address ID
    public Integer getAddressId() {
        return addressId;
    }

    // Setter for address ID
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}