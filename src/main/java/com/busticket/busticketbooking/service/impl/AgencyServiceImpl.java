package com.busticket.busticketbooking.service.impl;

import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyRepo agencyRepo;

    @Override
    public String getAgencyCount() {
        return "Total agencies: " + agencyRepo.count();
    }
}