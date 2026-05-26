package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing physical address profiles.
 *
 * <p>Provides standard CRUD operations for addresses, mapping incoming JSON payloads
 * to validated DTOs and returning responses back to the client.</p>
 */
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    /** Service layer for address business logic operations. */
    private final AddressService addressService;

    /**
     * Constructor injection for AddressService dependency.
     *
     * @param addressService the address service layer bean
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public AddressResponseDTO addAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.addAddress(addressRequestDTO);
    }

    @GetMapping("/{id}")
    public AddressResponseDTO getAddressById(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }

    @GetMapping
    public List<AddressResponseDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PutMapping("/{id}")
    public AddressResponseDTO updateAddress(@PathVariable Integer id,
                                            @Valid @RequestBody AddressRequestDTO addressRequestDTO) {
        return addressService.updateAddress(id, addressRequestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Integer id) {
        return addressService.deleteAddress(id);
    }
}