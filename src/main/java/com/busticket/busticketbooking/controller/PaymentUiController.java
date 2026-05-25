package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.PaymentDTO.PaymentRequestDTO;
import com.busticket.busticketbooking.dto.PaymentDTO.PaymentResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/payments")
public class PaymentUiController {

    private final PaymentService paymentService;

    public PaymentUiController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // LIST — paginated table of all payments
    @GetMapping
    public String listPayments(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        Page<PaymentResponseDTO> paymentPage = paymentService.getPaymentPage(safePageIndex, size);

        model.addAttribute("paymentPage", paymentPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "payment/list";
    }

    // SHOW CREATE FORM — POST /api/v1/payments
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("payment")) {
            model.addAttribute("payment", new PaymentRequestDTO());
        }
        model.addAttribute("isEdit", false);
        return "payment/form";
    }

    // CREATE — POST /api/v1/payments
    @PostMapping
    public String createPayment(
            @Valid @ModelAttribute("payment") PaymentRequestDTO payment,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "payment/form";
        }

        try {
            paymentService.makePayment(payment);
            redirectAttributes.addFlashAttribute("successMessage", "Payment recorded successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/payments";
    }

    // SHOW STATUS UPDATE FORM — GET /api/v1/payments/{paymentId} (prefilled)
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            PaymentResponseDTO existing = paymentService.getPaymentDetails(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + id + " not found"));

            // Only status is editable via PATCH /api/v1/payments/{id}/status
            PaymentRequestDTO form = new PaymentRequestDTO();
            form.setBookingId(existing.getBookingId());
            form.setAmount(existing.getAmount());
            form.setPaymentStatus(existing.getPaymentStatus());

            model.addAttribute("payment", form);
            model.addAttribute("paymentId", id);
            model.addAttribute("isEdit", true);
            return "payment/form";
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/payments";
        }
    }

    // UPDATE STATUS — PATCH /api/v1/payments/{paymentId}/status
    @PatchMapping("/{id}")
    public String updatePaymentStatus(
            @PathVariable Integer id,
            @ModelAttribute("payment") PaymentRequestDTO payment,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.updatePaymentStatus(id, payment.getPaymentStatus());
            redirectAttributes.addFlashAttribute("successMessage", "Payment status updated successfully.");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/payments";
    }

    // DELETE — perform actual database deletion of a payment record
    @DeleteMapping("/{id}")
    public String deletePayment(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.deletePayment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Payment record deleted successfully.");
        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/ui/payments";
    }
}
