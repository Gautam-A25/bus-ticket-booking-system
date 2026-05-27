package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.CustomerDTO.CustomerRequestDTO;
import com.busticket.busticketbooking.dto.CustomerDTO.CustomerResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.PageImpl;
import java.util.List;

/**
 * Web UI Controller that handles browser-based CRUD requests for Customer profiles.
 *
 * <p>Exposes interactive Thymeleaf-based views for listing customer records, managing passenger accounts,
 * and performing customer deactivation.</p>
 */
@Controller
@RequestMapping("/ui/customers")
public class CustomerUiController {

    /** Service layer for customer database operations. */
    private final CustomerService customerService;

    /**
     * Constructor injection for CustomerService dependency.
     *
     * @param customerService the customer service layer bean
     */
    public CustomerUiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            @RequestParam(name = "searchId", required = false) Integer searchId,
            Model model
    ) {

        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        if (searchId != null) {
            try {
                CustomerResponseDTO existing = customerService.getCustomerById(searchId);
                Page<CustomerResponseDTO> customerPage = new PageImpl<>(List.of(existing), org.springframework.data.domain.PageRequest.of(0, 1), 1);
                model.addAttribute("customerPage", customerPage);
                model.addAttribute("currentPage", 1);
                model.addAttribute("pageSize", size);
                model.addAttribute("searchId", searchId);
                return "customer/list";
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", ex.getMessage());
            }
        }

        Page<CustomerResponseDTO> customerPage =
                customerService.getCustomerPage(safePageIndex, size);

        model.addAttribute("customerPage", customerPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "customer/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new CustomerRequestDTO());
        }

        model.addAttribute("isEdit", false);

        return "customer/form";
    }

    @PostMapping
    public String createCustomer(
            @Valid @ModelAttribute("customer") CustomerRequestDTO customer,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("isEdit", false);

            return "customer/form";
        }

        try {

            customerService.createCustomer(customer);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Customer created successfully."
            );

            return "redirect:/ui/customers";

        } catch (Exception ex) {

            model.addAttribute("isEdit", false);

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "customer/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        try {

            CustomerResponseDTO existing =
                    customerService.getCustomerById(id);

            CustomerRequestDTO customer =
                    new CustomerRequestDTO();

            customer.setName(existing.getName());
            customer.setEmail(existing.getEmail());
            customer.setPhone(existing.getPhone());
            customer.setAddressId(existing.getAddressId());

            model.addAttribute("customerId", id);
            model.addAttribute("customer", customer);
            model.addAttribute("isEdit", true);

            return "customer/form";

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "redirect:/ui/customers";
        }
    }

    @PutMapping("/{id}")
    public String updateCustomer(
            @PathVariable Integer id,
            @Valid @ModelAttribute("customer") CustomerRequestDTO customer,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("customerId", id);
            model.addAttribute("isEdit", true);

            return "customer/form";
        }

        try {

            customerService.updateCustomer(id, customer);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Customer updated successfully."
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/customers";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {

            String message = customerService.deleteCustomer(id);

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

        return "redirect:/ui/customers";
    }
}