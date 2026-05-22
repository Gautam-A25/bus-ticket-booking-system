package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DriverService {

    DriverResponseDTO createDriver(DriverRequestDTO dto);

    List<DriverResponseDTO> getAllDrivers();

    Page<DriverResponseDTO> getDriverPage(int page, int size);

    DriverResponseDTO getDriverById(Integer id);

    List<DriverResponseDTO> getDriversByOffice(Integer officeId);

    DriverResponseDTO updateDriver(Integer driverId, DriverRequestDTO dto);

    String deleteDriver(Integer id);
}
