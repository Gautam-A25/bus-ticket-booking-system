package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.addressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.addressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

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