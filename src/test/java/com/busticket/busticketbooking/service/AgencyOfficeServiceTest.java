// Total tests: 10
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeRequestDTO;
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;
import com.busticket.busticketbooking.entity.Address;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.entity.AgencyOffice;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AddressRepo;
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.impl.AgencyOfficeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgencyOfficeServiceTest {

    @Mock
    private AgencyOfficeRepo agencyOfficeRepo;

    @Mock
    private AgencyRepo agencyRepo;

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private AgencyOfficeServiceImpl agencyOfficeService;

    private AgencyOfficeRequestDTO requestDto;
    private Agency agency;
    private Address address;
    private AgencyOffice office;

    @BeforeEach
    public void setUp() {
        requestDto = new AgencyOfficeRequestDTO();
        requestDto.setAddressId(10);
        requestDto.setOfficeMail("delhi@travels.com");
        requestDto.setOfficeContactPersonName("Verma Ji");
        requestDto.setOfficeContactNumber("9999999999");

        agency = new Agency();
        agency.setId(5);
        agency.setName("Super Travels");

        address = new Address();
        address.setId(10);
        address.setCity("Delhi");

        office = new AgencyOffice();
        office.setId(20);
        office.setAgency(agency);
        office.setAddress(address);
        office.setOfficeMail("delhi@travels.com");
        office.setOfficeContactPersonName("Verma Ji");
        office.setOfficeContactNumber("9999999999");
    }

    /**
     * 1. testAddAgencyOffice_Success - Verify that an agency office is added successfully under valid agency and address.
     */
    @Test
    public void testAddAgencyOffice_Success() {
        when(agencyRepo.findById(5)).thenReturn(Optional.of(agency));
        when(addressRepo.findById(10)).thenReturn(Optional.of(address));
        when(agencyOfficeRepo.save(any(AgencyOffice.class))).thenReturn(office);

        AgencyOfficeResponseDTO response = agencyOfficeService.addAgencyOffice(5, requestDto);

        assertNotNull(response);
        assertEquals(20, response.getOfficeId());
        assertEquals("delhi@travels.com", response.getOfficeMail());
    }

    /**
     * 2. testAddAgencyOffice_AgencyNotFound_ThrowsException - Verify missing Agency ID throws ResourceNotFoundException.
     */
    @Test
    public void testAddAgencyOffice_AgencyNotFound_ThrowsException() {
        when(agencyRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> agencyOfficeService.addAgencyOffice(999, requestDto));
    }

    /**
     * 3. testAddAgencyOffice_AddressNotFound_ThrowsException - Verify missing Address ID throws ResourceNotFoundException.
     */
    @Test
    public void testAddAgencyOffice_AddressNotFound_ThrowsException() {
        when(agencyRepo.findById(5)).thenReturn(Optional.of(agency));
        when(addressRepo.findById(10)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> agencyOfficeService.addAgencyOffice(5, requestDto));
    }

    /**
     * 4. testGetAgencyOfficeById_Success - Verify that an office is successfully retrieved by ID.
     */
    @Test
    public void testGetAgencyOfficeById_Success() {
        when(agencyOfficeRepo.findById(20)).thenReturn(Optional.of(office));
        AgencyOfficeResponseDTO response = agencyOfficeService.getAgencyOfficeById(20);
        assertNotNull(response);
        assertEquals(20, response.getOfficeId());
    }

    /**
     * 5. testGetAgencyOfficeById_NotFound_ThrowsException - Verify that requesting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetAgencyOfficeById_NotFound_ThrowsException() {
        when(agencyOfficeRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> agencyOfficeService.getAgencyOfficeById(999));
    }

    /**
     * 6. testGetAgencyOfficesByAgencyId_Success - Verify that all offices belonging to an agency are retrieved.
     */
    @Test
    public void testGetAgencyOfficesByAgencyId_Success() {
        when(agencyRepo.existsById(5)).thenReturn(true);
        when(agencyOfficeRepo.findByAgency_Id(5)).thenReturn(Arrays.asList(office));

        List<AgencyOfficeResponseDTO> response = agencyOfficeService.getAgencyOfficesByAgencyId(5);
        assertEquals(1, response.size());
    }

    /**
     * 7. testGetAgencyOfficesByAgencyId_AgencyNotFound_ThrowsException - Verify missing Agency ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetAgencyOfficesByAgencyId_AgencyNotFound_ThrowsException() {
        when(agencyRepo.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> agencyOfficeService.getAgencyOfficesByAgencyId(999));
    }

    /**
     * 8. testUpdateAgencyOffice_Success - Verify that an office is updated successfully.
     */
    @Test
    public void testUpdateAgencyOffice_Success() {
        when(agencyOfficeRepo.findById(20)).thenReturn(Optional.of(office));
        when(agencyRepo.findById(5)).thenReturn(Optional.of(agency));
        when(addressRepo.findById(10)).thenReturn(Optional.of(address));
        when(agencyOfficeRepo.save(any(AgencyOffice.class))).thenReturn(office);

        AgencyOfficeResponseDTO response = agencyOfficeService.updateAgencyOffice(20, requestDto);
        assertNotNull(response);
        assertEquals(20, response.getOfficeId());
    }

    /**
     * 9. testDeleteAgencyOffice_Success - Verify that deleting an existing office returns success message.
     */
    @Test
    public void testDeleteAgencyOffice_Success() {
        when(agencyOfficeRepo.existsById(20)).thenReturn(true);
        doNothing().when(agencyOfficeRepo).deleteById(20);
        String response = agencyOfficeService.deleteAgencyOffice(20);
        assertEquals("Agency office deleted successfully", response);
    }

    /**
     * 10. testDeleteAgencyOffice_NotFound_ThrowsException - Verify that deleting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testDeleteAgencyOffice_NotFound_ThrowsException() {
        when(agencyOfficeRepo.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> agencyOfficeService.deleteAgencyOffice(999));
    }
}
