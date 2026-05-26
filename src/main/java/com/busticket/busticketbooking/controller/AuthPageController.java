package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.AuthDTO.RegisterRequestDTO;
import com.busticket.busticketbooking.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web UI Controller that serves login and registration web forms.
 *
 * <p>Validates registration requests submitted from the registration page, routing users
 * back to login once their account is successfully created.</p>
 */
@Controller
public class AuthPageController {

    /** Service layer for register/login business logic operations. */
    private final AuthService authService;

    /**
     * Constructor injection for AuthService dependency.
     *
     * @param authService the authentication service layer bean
     */
    public AuthPageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        try {
            RegisterRequestDTO request = new RegisterRequestDTO();
            request.setUsername(username);
            request.setPassword(password);

            authService.register(request);

            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/register";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Could not create account");
            return "redirect:/register";
        }
    }
}