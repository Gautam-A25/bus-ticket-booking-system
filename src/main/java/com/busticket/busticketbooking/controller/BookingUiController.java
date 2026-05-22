package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.BookingDTO.BookingRequestDTO;
import com.busticket.busticketbooking.dto.BookingDTO.BookingResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/bookings")
public class BookingUiController {

    private final BookingService bookingService;

    public BookingUiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String listBookings(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            Model model
    ) {

        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        Page<BookingResponseDTO> bookingPage =
                bookingService.getBookingPage(safePageIndex, size);

        model.addAttribute("bookingPage", bookingPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "booking/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        if (!model.containsAttribute("booking")) {
            model.addAttribute("booking", new BookingRequestDTO());
        }

        return "booking/form";
    }

    @PostMapping
    public String createBooking(
            @RequestParam Integer tripId,

            @Valid
            @ModelAttribute("booking")
            BookingRequestDTO booking,

            BindingResult bindingResult,

            RedirectAttributes redirectAttributes,

            Model model
    ) {

        if (bindingResult.hasErrors()) {

            return "booking/form";
        }

        try {

            bookingService.createBooking(
                    tripId,
                    booking
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Booking created successfully."
            );

            return "redirect:/ui/bookings";

        } catch (Exception ex) {

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "booking/form";
        }
    }

    @PatchMapping("/{id}/cancel")
    public String cancelBooking(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String message = bookingService.cancelBooking(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    message
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/bookings";
    }
}