package com.busticket.busticketbooking.dto.BusDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/*
 * DTO = Data Transfer Object
 *
 * This class is used to receive request data
 * from client/API while creating or updating Bus.
 *
 * Request DTO helps separate API layer
 * from Entity/database layer.
 */
public class BusRequestDTO {

    /*
     * Stores office ID associated with bus.
     *
     * @NotNull ensures office ID is mandatory.
     */
    @NotNull(message = "Office ID is required")
    private Integer officeId;

    /*
     * Stores bus registration number.
     *
     * @NotBlank ensures value is not null or empty.
     *
     * @Size limits maximum length to 20 characters.
     *
     * @Pattern validates registration number format.
     */
    @NotBlank(message = "Registration number is required")
    @Size(
            max = 20,
            message = "Registration number must not exceed 20 characters"
    )
    @Pattern(
            regexp = "^[A-Za-z0-9\\- ]{3,20}$",
            message = "Registration number format is invalid"
    )
    private String registrationNumber;

    /*
     * Stores total seating capacity of bus.
     *
     * @NotNull ensures capacity is mandatory.
     *
     * @Positive ensures value is greater than 0.
     */
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;

    /*
     * Stores bus type like:
     * AC, Sleeper, Volvo etc.
     *
     * @NotBlank ensures value is mandatory.
     *
     * @Size restricts maximum length.
     */
    @NotBlank(message = "Bus type is required")
    @Size(
            max = 30,
            message = "Bus type must not exceed 30 characters"
    )
    private String type;

    /*
     * Default constructor.
     *
     * Required for object creation by Spring/Jackson.
     */
    public BusRequestDTO() {
    }

    /*
     * Returns office ID.
     */
    public Integer getOfficeId() {
        return officeId;
    }

    /*
     * Sets office ID.
     */
    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    /*
     * Returns registration number.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /*
     * Sets registration number.
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /*
     * Returns seating capacity.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /*
     * Sets seating capacity.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /*
     * Returns bus type.
     */
    public String getType() {
        return type;
    }

    /*
     * Sets bus type.
     */
    public void setType(String type) {
        this.type = type;
    }
}