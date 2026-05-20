package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.dto.AddressDTO.AddressRequestDTO;
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.mapper.AddressMapper;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.service.AddressService;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO) {
        Address address = AddressMapper.toEntity(addressRequestDTO);
        Address savedAddress = addressRepo.save(address);
        return AddressMapper.toResponseDTO(savedAddress);
    }

    @Override
    public AddressResponseDTO getAddressById(Integer id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + id + " not found"));
        return AddressMapper.toResponseDTO(address);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepo.findAll()
                .stream()
                .map(AddressMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponseDTO updateAddress(Integer id, AddressRequestDTO addressRequestDTO) {
        Address existingAddress = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with ID " + id + " not found"));

        existingAddress.setAddress(addressRequestDTO.getAddress());
        existingAddress.setCity(addressRequestDTO.getCity());
        existingAddress.setState(addressRequestDTO.getState());
        existingAddress.setZipCode(addressRequestDTO.getZipCode());

        Address updatedAddress = addressRepo.save(existingAddress);
        return AddressMapper.toResponseDTO(updatedAddress);
    }

    @Override
    public String deleteAddress(Integer id) {
        if (!addressRepo.existsById(id)) {
            throw new ResourceNotFoundException("Address with ID " + id + " not found");
        }
        addressRepo.deleteById(id);
        return "Address deleted successfully";
    }
}