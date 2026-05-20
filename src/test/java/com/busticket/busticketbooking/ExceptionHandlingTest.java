package com.busticket.busticketbooking;

import com.busticket.busticketbooking.controller.BusController;
import com.busticket.busticketbooking.controller.PaymentController;
import com.busticket.busticketbooking.exception.GlobalExceptionHandler;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.security.CustomUserDetailsService;
import com.busticket.busticketbooking.service.BusService;
import com.busticket.busticketbooking.service.PaymentService;
import com.busticket.busticketbooking.security.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest({BusController.class, PaymentController.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class ExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BusService busService;

    @MockitoBean
    private PaymentService paymentService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldReturn404WhenBusNotFound() throws Exception {
        when(busService.getBusById(999))
                .thenThrow(new ResourceNotFoundException("Bus not found"));

        mockMvc.perform(get("/api/v1/buses/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bus not found"));
    }

    @Test
    void shouldReturn404WhenPaymentNotFound() throws Exception {
        when(paymentService.getPaymentDetails(99999))
                .thenThrow(new ResourceNotFoundException("Payment not found"));

        mockMvc.perform(get("/api/v1/payments/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Payment not found"));
    }
}