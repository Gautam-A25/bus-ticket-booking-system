package com.busticket.busticketbooking.dto.CustomerDTO;

import jakarta.validation.constraints.*;

// DTO used to receive customer request data
public class CustomerRequestDTO {

    // Stores customer name
    @NotBlank(message = "Customer name is required")

    // Validates name length between 3 and 50 characters
    @Size(
            min = 3,
            max = 50,
            message = "Customer name must be between 3 and 50 characters"
    )

    // Allows only alphabets and spaces in name
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Customer name must contain only alphabets"
    )
    private String name;

    // Stores customer email address
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Customer email format is invalid"
    )

    // Ensures email field is not empty
    @NotBlank(message = "Customer email is required")
    private String email;

    // Stores customer phone number
    @NotBlank(message = "Customer phone number is required")

    // Validates 10-digit phone number format
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Customer phone number must contain exactly 10 digits"
    )
    private String phone;

    // Stores address ID linked to customer
    @NotNull(message = "Customer address ID is required")
    private Integer addressId;

    // Default constructor
    public CustomerRequestDTO() {
    }

    // Parameterized constructor for object initialization
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

    // Returns customer name
    public String getName() {
        return name;
    }

    // Sets customer name
    public void setName(String name) { this.name = name; }

    // Returns customer email
    public String getEmail() {
        return email;
    }

    // Sets customer email
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns customer phone number
    public String getPhone() {
        return phone;
    }

    // Sets customer phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Returns address ID
    public Integer getAddressId() {
        return addressId;
    }

    // Sets address ID
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}