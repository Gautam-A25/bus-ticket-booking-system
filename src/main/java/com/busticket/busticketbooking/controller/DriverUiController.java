package com.busticket.busticketbooking.controller;

+/*
 * Importing Driver Request DTO
 * Used to receive driver form data from UI
 */
import com.busticket.busticketbooking.dto.DriverDTO.DriverRequestDTO;

/*
 * Importing Driver Response DTO
 * Used to send driver data to UI
 */
import com.busticket.busticketbooking.dto.DriverDTO.DriverResponseDTO;

/*
 * Importing Address Response DTO
 * Used to show address list in dropdown
 */
import com.busticket.busticketbooking.dto.AddressDTO.AddressResponseDTO;

/*
 * Importing Agency Office Response DTO
 * Used to show office list in dropdown
 */
import com.busticket.busticketbooking.dto.AgencyOfficeDTO.AgencyOfficeResponseDTO;

/*
 * Custom Exception
 * Thrown when resource is not found
 */
import com.busticket.busticketbooking.exception.ResourceNotFoundException;

/*
 * Service layer for Driver operations
 */
import com.busticket.busticketbooking.service.DriverService;

/*
 * Service layer for Address operations
 */
import com.busticket.busticketbooking.service.AddressService;

/*
 * Repository for AgencyOffice table
 */
import com.busticket.busticketbooking.repo.AgencyOfficeRepo;

/*
 * Mapper used to convert Entity -> DTO
 */
import com.busticket.busticketbooking.mapper.AgencyOfficeMapper;

/*
 * Validation annotation
 */
import jakarta.validation.Valid;

/*
 * Spring Page object for pagination
 */
import org.springframework.data.domain.Page;

/*
 * Marks this class as MVC Controller
 */
import org.springframework.stereotype.Controller;

/*
 * Used to send data from controller -> HTML
 */
import org.springframework.ui.Model;

/*
 * Stores validation errors
 */
import org.springframework.validation.BindingResult;

/*
 * Spring MVC annotations
 */
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Used for redirect messages
 */
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Controller
 * Marks this class as UI Controller
 */
@Controller

/*
 * Base URL for all Driver UI endpoints
 */
@RequestMapping("/ui/drivers")
public class DriverUiController {

    /*
     * Driver Service object
     */
    private final DriverService driverService;

    /*
     * Address Service object
     */
    private final AddressService addressService;

    /*
     * Agency Office Repository object
     */
    private final AgencyOfficeRepo agencyOfficeRepo;

    /*
     * Constructor Injection
     * Spring automatically injects dependencies
     */
    public DriverUiController(
            DriverService driverService,
            AddressService addressService,
            AgencyOfficeRepo agencyOfficeRepo
    ) {
        this.driverService = driverService;
        this.addressService = addressService;
        this.agencyOfficeRepo = agencyOfficeRepo;
    }

    /*
     * GET API
     * Used to display Driver List page
     */
    @GetMapping
    public String listDrivers(

            /*
             * Current page number
             */
            @RequestParam(name = "page", defaultValue = "1") int page,

            /*
             * Number of records per page
             */
            @RequestParam(name = "size", defaultValue = "6") int size,

            /*
             * Model object to send data to UI
             */
            Model model
    ) {

        /*
         * Prevent page value less than 1
         */
        int requestedPage = Math.max(page, 1);

        /*
         * Spring pagination starts from 0
         */
        int safePageIndex = requestedPage - 1;

        /*
         * Fetch paginated drivers
         */
        Page<DriverResponseDTO> driverPage =
                driverService.getDriverPage(safePageIndex, size);

        /*
         * Send data to HTML page
         */
        model.addAttribute("driverPage", driverPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        /*
         * Return driver list HTML page
         */
        return "driver/list";
    }

    /*
     * GET API
     * Opens Create Driver Form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        /*
         * Add empty object only if not already present
         */
        if (!model.containsAttribute("driver")) {

            model.addAttribute(
                    "driver",
                    new DriverRequestDTO()
            );
        }

        /*
         * Populate dropdown data
         */
        populateFormModels(model);

        /*
         * isEdit = false
         * Means Create Mode
         */
        model.addAttribute("isEdit", false);

        /*
         * Open form page
         */
        return "driver/form";
    }

    /*
     * POST API
     * Save new Driver
     */
    @PostMapping
    public String createDriver(

            /*
             * Receive form data
             * @Valid triggers validations
             */
            @Valid @ModelAttribute("driver")
            DriverRequestDTO driver,

            /*
             * Stores validation errors
             */
            BindingResult bindingResult,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        /*
         * If validation fails
         */
        if (bindingResult.hasErrors()) {

            populateFormModels(model);

            model.addAttribute("isEdit", false);

            return "driver/form";
        }

        try {

            /*
             * Save driver
             */
            driverService.createDriver(driver);

            /*
             * Success flash message
             */
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Driver created successfully."
            );

        } catch (Exception ex) {

            populateFormModels(model);

            model.addAttribute("isEdit", false);

            /*
             * Error message
             */
            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "driver/form";
        }

        /*
         * Redirect to list page
         */
        return "redirect:/ui/drivers";
    }

    /*
     * GET API
     * Open Edit Form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(

            /*
             * Driver ID from URL
             */
            @PathVariable Integer id,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        try {

            /*
             * Fetch driver data
             */
            DriverResponseDTO existing =
                    driverService.getDriverById(id);

            /*
             * Create Request DTO object
             */
            DriverRequestDTO driver =
                    new DriverRequestDTO();

            /*
             * Set existing values
             */
            driver.setName(existing.getName());
            driver.setPhone(existing.getPhone());
            driver.setLicenseNumber(existing.getLicenseNumber());
            driver.setOfficeId(existing.getOfficeId());
            driver.setAddressId(existing.getAddressId());

            /*
             * Send data to UI
             */
            model.addAttribute("driverId", id);
            model.addAttribute("driver", driver);

            populateFormModels(model);

            /*
             * isEdit = true
             */
            model.addAttribute("isEdit", true);

            return "driver/form";

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "redirect:/ui/drivers";
        }
    }

    /*
     * PUT API
     * Update existing Driver
     */
    @PutMapping("/{id}")
    public String updateDriver(

            @PathVariable Integer id,

            @Valid @ModelAttribute("driver")
            DriverRequestDTO driver,

            BindingResult bindingResult,

            Model model,

            RedirectAttributes redirectAttributes
    ) {

        /*
         * Validation error handling
         */
        if (bindingResult.hasErrors()) {

            model.addAttribute("driverId", id);

            populateFormModels(model);

            model.addAttribute("isEdit", true);

            return "driver/form";
        }

        try {

            /*
             * Update Driver
             */
            driverService.updateDriver(id, driver);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Driver updated successfully."
            );

        } catch (Exception ex) {

            model.addAttribute("driverId", id);

            populateFormModels(model);

            model.addAttribute("isEdit", true);

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "driver/form";
        }

        return "redirect:/ui/drivers";
    }

    /*
     * DELETE API
     * Delete Driver by ID
     */
    @DeleteMapping("/{id}")
    public String deleteDriver(

            @PathVariable Integer id,

            RedirectAttributes redirectAttributes
    ) {

        try {

            String successMessage =
                    driverService.deleteDriver(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    successMessage
            );

        } catch (ResourceNotFoundException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/ui/drivers";
    }

    /*
     * Common Method
     * Used to load dropdown data
     */
    private void populateFormModels(Model model) {

        /*
         * Fetch all addresses
         */
        List<AddressResponseDTO> addresses =
                addressService.getAllAddresses();

        /*
         * Fetch all offices
         */
        List<AgencyOfficeResponseDTO> offices =
                agencyOfficeRepo.findAll()
                        .stream()
                        .map(AgencyOfficeMapper::toResponseDTO)
                        .collect(Collectors.toList());

        /*
         * Send dropdown data to UI
         */
        model.addAttribute("addresses", addresses);

        model.addAttribute("offices", offices);
    }
}
