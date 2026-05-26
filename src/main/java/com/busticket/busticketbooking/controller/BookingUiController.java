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
import org.springframework.data.domain.PageImpl;
import java.util.List;

/**
 * Web UI Controller that handles web-based front-end requests for Bookings.
 *
 * <p>Uses Thymeleaf templates to list ticket reservations, book new seats for scheduled trips,
 * and cancel active reservations from the dashboard view.</p>
 */
@Controller
@RequestMapping("/ui/bookings")
public class BookingUiController {

    /** Service layer for booking operations. */
    private final BookingService bookingService;

    /**
     * Constructor injection for BookingService dependency.
     *
     * @param bookingService the booking service layer bean
     */
    public BookingUiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String listBookings(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "searchId", required = false) Integer searchId,
            Model model
    ) {

        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                BookingResponseDTO existing = bookingService.getBookingById(searchId);
                Page<BookingResponseDTO> bookingPage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("bookingPage", bookingPage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "booking/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

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