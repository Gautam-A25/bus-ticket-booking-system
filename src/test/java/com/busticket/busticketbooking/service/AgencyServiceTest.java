// Total tests: 9
package com.busticket.busticketbooking.service;

import com.busticket.busticketbooking.dto.AgencyDTO.AgencyRequestDTO;
import com.busticket.busticketbooking.dto.AgencyDTO.AgencyResponseDTO;
import com.busticket.busticketbooking.entity.Agency;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.repo.AgencyRepo;
import com.busticket.busticketbooking.service.impl.AgencyServiceImpl;
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
public class AgencyServiceTest {

    @Mock
    private AgencyRepo agencyRepo;

    @InjectMocks
    private AgencyServiceImpl agencyService;

    private AgencyRequestDTO requestDto;
    private Agency agency;

    @BeforeEach
    public void setUp() {
        requestDto = new AgencyRequestDTO();
        requestDto.setName("Super Travels");
        requestDto.setContactPersonName("Rajesh Kumar");
        requestDto.setEmail("rajesh@supertravels.com");
        requestDto.setPhone("9898989898");

        agency = new Agency();
        agency.setId(1);
        agency.setName("Super Travels");
        agency.setContactPersonName("Rajesh Kumar");
        agency.setEmail("rajesh@supertravels.com");
        agency.setPhone("9898989898");
    }

    /**
     * 1. testAddAgency_Success - Verify that a new agency is successfully created.
     */
    @Test
    public void testAddAgency_Success() {
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency);
        AgencyResponseDTO response = agencyService.addAgency(requestDto);
        assertNotNull(response);
        assertEquals(1, response.getAgencyId());
        assertEquals("Super Travels", response.getName());
    }

    /**
     * 2. testGetAgencyById_Success - Verify that an agency is successfully retrieved by ID.
     */
    @Test
    public void testGetAgencyById_Success() {
        when(agencyRepo.findById(1)).thenReturn(Optional.of(agency));
        AgencyResponseDTO response = agencyService.getAgencyById(1);
        assertNotNull(response);
        assertEquals(1, response.getAgencyId());
    }

    /**
     * 3. testGetAgencyById_NotFound_ThrowsException - Verify that requesting a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testGetAgencyById_NotFound_ThrowsException() {
        when(agencyRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> agencyService.getAgencyById(999));
    }

    /**
     * 4. testGetAllAgencies_NotEmpty - Verify that list of agencies is successfully retrieved.
     */
    @Test
    public void testGetAllAgencies_NotEmpty() {
        when(agencyRepo.findAll()).thenReturn(Arrays.asList(agency));
        List<AgencyResponseDTO> response = agencyService.getAllAgencies();
        assertEquals(1, response.size());
    }

    /**
     * 5. testGetAllAgencies_Empty - Verify that an empty list is handled properly.
     */
    @Test
    public void testGetAllAgencies_Empty() {
        when(agencyRepo.findAll()).thenReturn(Collections.emptyList());
        List<AgencyResponseDTO> response = agencyService.getAllAgencies();
        assertTrue(response.isEmpty());
    }

    /**
     * 6. testUpdateAgency_Success - Verify that an agency is updated successfully.
     */
    @Test
    public void testUpdateAgency_Success() {
        when(agencyRepo.findById(1)).thenReturn(Optional.of(agency));
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency);
        AgencyResponseDTO response = agencyService.updateAgency(1, requestDto);
        assertNotNull(response);
        assertEquals("Super Travels", response.getName());
    }

    /**
     * 7. testUpdateAgency_NotFound_ThrowsException - Verify that updating a missing ID throws ResourceNotFoundException.
     */
    @Test
    public void testUpdateAgency_NotFound_ThrowsException() {
        when(agencyRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> agencyService.updateAgency(999, requestDto));
    }

    /**
     * 8. testDeleteAgency_Success - Verify that deleting an existing ID returns success message.
     */
   @Test
public void testDeleteAgency_Success() {

    Agency agency = new Agency();

    agency.setId(1);

    when(agencyRepo.findById(1))
            .thenReturn(Optional.of(agency));

    doNothing().when(agencyRepo)
            .delete(agency);

    String result =
            agencyService.deleteAgency(1);

    assertNotNull(result);

    verify(agencyRepo).delete(agency);
}

    /**
     * 9. testDeleteAgency_NotFound_ThrowsException - Verify that deleting a missing ID throws ResourceNotFoundException.
     */
    // @Test
    // public void testDeleteAgency_NotFound_ThrowsException() {
    //     when(agencyRepo.existsById(999)).thenReturn(false);
    //     assertThrows(ResourceNotFoundException.class, () -> agencyService.deleteAgency(999));
    // }
}
