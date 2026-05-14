package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.service.AgencyOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgencyOfficeController {

    @Autowired
    private AgencyOfficeService agencyOfficeService;

    @GetMapping("/offices/count")
    public String getOfficeCount() {
        return agencyOfficeService.getOfficeCount();
    }
}