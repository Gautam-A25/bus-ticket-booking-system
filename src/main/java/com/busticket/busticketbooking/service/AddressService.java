package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {

    AddressResponseDTO addAddress(
            AddressRequestDTO addressRequestDTO);

    AddressResponseDTO getAddressById(
            Integer id);

    List<AddressResponseDTO> getAllAddresses();

    Page<AddressResponseDTO> getAddressPage(
            int page,
            int size);

    AddressResponseDTO updateAddress(
            Integer id,
            AddressRequestDTO addressRequestDTO);

    String deleteAddress(
            Integer id);
}