package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining all address management operations.
 *
 * <p>Addresses are shared records reused by {@code Customer}, {@code Driver},
 * and {@code AgencyOffice} entities. Deleting an address requires
 * unlinking it from those entities first and cascading through dependent
 * {@code Trip}, {@code Booking}, {@code Payment}, and {@code Review} records.</p>
 */
public interface AddressService {

    /** Creates and persists a new address from the given request data. */
    AddressResponseDTO addAddress(
            AddressRequestDTO addressRequestDTO);

    /** Returns the address with the given ID; throws 404 if not found. */
    AddressResponseDTO getAddressById(
            Integer id);

    /** Returns all addresses in the system (no pagination). */
    List<AddressResponseDTO> getAllAddresses();

    /**
     * Returns a paginated, ID-ascending slice of all addresses.
     *
     * @param page zero-based page index
     * @param size maximum number of records per page
     */
    Page<AddressResponseDTO> getAddressPage(
            int page,
            int size);

    /** Updates all fields of an existing address; throws 404 if not found. */
    AddressResponseDTO updateAddress(
            Integer id,
            AddressRequestDTO addressRequestDTO);

    /**
     * Deletes an address and cascades the change to all referencing entities:
     * nullifies the FK on drivers, offices, and customers;
     * deletes trips (and their bookings, payments, reviews) that use this address.
     *
     * @return a formatted summary string of the deleted record
     */
    String deleteAddress(
            Integer id);
}