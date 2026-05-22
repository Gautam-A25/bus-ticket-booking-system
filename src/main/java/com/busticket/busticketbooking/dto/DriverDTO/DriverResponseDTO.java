package com.busticket.busticketbooking.dto.DriverDTO;

import java.lang.Integer;

/*
 * DTO = Data Transfer Object
 *
 * This class is used to send Driver data
 * from backend to client/API response.
 *
 * Response DTO helps hide unnecessary
 * entity/database details from client.
 */
public class DriverResponseDTO {

    /*
     * Stores unique Driver ID.
     */
    private Integer id;

    /*
     * Stores associated Office ID.
     */
    private Integer officeId;

    /*
     * Stores associated Address ID.
     */
    private Integer addressId;

    /*
     * Stores driver's license number.
     */
    private String licenseNumber;

    /*
     * Stores driver name.
     */
    private String name;

    /*
     * Stores driver phone number.
     */
    private String phone;

    /*
     * Default constructor.
     *
     * Required for object creation.
     */
    public DriverResponseDTO() {
    }

    /*
     * Returns driver ID.
     */
    public Integer getId() {
        return id;
    }

    /*
     * Sets driver ID.
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
     * Returns address ID.
     */
    public Integer getAddressId() {
        return addressId;
    }

    /*
     * Sets address ID.
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /*
     * Returns driver's license number.
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /*
     * Sets driver's license number.
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /*
     * Returns driver name.
     */
    public String getName() {
        return name;
    }

    /*
     * Sets driver name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Returns driver phone number.
     */
    public String getPhone() {
        return phone;
    }

    /*
     * Sets driver phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}