package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public String getAddressCount() {
        return "Total addresses: " + addressRepo.count();
    }
}