package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping("/agencies/count")
    public String getAgencyCount() {
        return agencyService.getAgencyCount();
    }
}