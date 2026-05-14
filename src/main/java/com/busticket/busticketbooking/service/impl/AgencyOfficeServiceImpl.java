package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.service.AgencyOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyOfficeServiceImpl implements AgencyOfficeService {

    @Autowired
    private AgencyOfficeRepo agencyOfficeRepo;

    @Override
    public String getOfficeCount() {
        return "Total offices: " + agencyOfficeRepo.count();
    }
}