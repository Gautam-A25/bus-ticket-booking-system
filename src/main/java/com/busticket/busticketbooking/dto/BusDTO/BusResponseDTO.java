package com.busticket.busticketbooking.dto.BusDTO;

/*
 * DTO = Data Transfer Object
 *
 * This class is used to send Bus data
 * from backend to client/API response.
 *
 * Response DTO helps hide unnecessary
 * entity/database details from client.
 */
public class BusResponseDTO {

    /*
     * Stores unique Bus ID.
     */
    private Integer id;

    /*
     * Stores associated Office ID.
     */
    private Integer officeId;

    /*
     * Stores bus registration number.
     */
    private String registrationNumber;

    /*
     * Stores total seating capacity of bus.
     */
    private Integer capacity;

    /*
     * Stores bus type like:
     * AC, Sleeper, Volvo etc.
     */
    private String type;

    /*
     * Default constructor.
     *
     * Required for object creation.
     */
    public BusResponseDTO() {
    }

    /*
     * Returns bus ID.
     */
    public Integer getId() {
        return id;
    }

    /*
     * Sets bus ID.
     */
    public void setId(Integer id) {
        this.id = id;
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