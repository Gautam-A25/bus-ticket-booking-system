package com.busticket.busticketbooking.dto.CustomerDTO;

import jakarta.validation.constraints.*;

// DTO used for customer request data
public class CustomerRequestDTO {

    // Customer name
    @NotBlank(message = "Customer name is required")
    @Size(
            min = 3,
            max = 50,
            message = "Customer name must be between 3 and 50 characters"
    )
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Customer name must contain only alphabets"
    )
    private String name;

    // Customer email
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Customer email format is invalid"
    )
    @NotBlank(message = "Customer email is required")
    private String email;

    // Customer phone number
    @NotBlank(message = "Customer phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Customer phone number must contain exactly 10 digits"
    )
    private String phone;

    // Address ID associated with customer
    @NotNull(message = "Customer address ID is required")
    private Integer addressId;

    // Default constructor
    public CustomerRequestDTO() {
    }

    // Parameterized constructor
    public CustomerRequestDTO(
            String name,
            String email,
            String phone,
            Integer addressId
    ) {

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressId = addressId;
    }

    // Getter for customer name
    public String getName() {
        return name;
    }

    // Setter for customer name
    public void setName(String name) { this.name = name; }

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