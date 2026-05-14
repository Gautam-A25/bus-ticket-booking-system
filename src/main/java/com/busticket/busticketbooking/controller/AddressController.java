package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses/count")
    public String getAddressCount() {
        return addressService.getAddressCount();
    }
}