package com.busticket.busticketbooking.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class WebUIController {

    private final List<ModuleInfo> modules = new ArrayList<>();

    @PostConstruct
    public void init() {
        modules.clear();

        // 1. Address Module
        modules.add(new ModuleInfo(
                "address",
                "Address Module",
                "📍",
                "Manages physical address records, geographic coordinates, and regional location structures.",
                "/ui/addresses",
                buildAddressEndpoints()
        ));

        // 2. Agency Module
        modules.add(new ModuleInfo(
                "agency",
                "Agency Module",
                "🏢",
                "Handles transport agency registrations, corporate structures, contact info, and partner statuses.",
                "/ui/agencies",
                buildAgencyEndpoints()
        ));

        // 3. Agency Office Module
        modules.add(new ModuleInfo(
                "office",
                "Agency Office Module",
                "🏬",
                "Manages regional branch offices under partner agencies, associating them with locations and addresses.",
                "/ui/offices",
                buildOfficeEndpoints()
        ));

        // 4. Bus Module
        modules.add(new ModuleInfo(
                "bus",
                "Bus Module",
                "🚌",
                "Administers physical bus assets, matching them to offices, seating capacities, and custom transport types.",
                "/ui/buses",
                buildBusEndpoints()
        ));

        // 5. Driver Module
        modules.add(new ModuleInfo(
                "driver",
                "Driver Module",
                "👨‍✈️",
                "Coordinates transport drivers, license status checks, office assignments, and active rosters.",
                "/ui/drivers",
                buildDriverEndpoints()
        ));

        // 6. Customer Module
        modules.add(new ModuleInfo(
                "customer",
                "Customer Module",
                "👤",
                "Manages passenger user profiles, contact registration, account statuses, and system roles.",
                "/ui/customers",
                buildCustomerEndpoints()
        ));

        // 7. Route Module
        modules.add(new ModuleInfo(
                "route",
                "Route Module",
                "🗺️",
                "Defines mapping of transit pathways between cities, travel durations, and stopover points.",
                "/ui/routes",
                buildRouteEndpoints()
        ));

        // 8. Trip Module
        modules.add(new ModuleInfo(
                "trip",
                "Trip Module",
                "📅",
                "Schedules transit trips for routes, managing active dates, fares, and passenger bookings.",
                "/ui/trips",
                buildTripEndpoints()
        ));

        // 9. Booking Module
        modules.add(new ModuleInfo(
                "booking",
                "Booking Module",
                "🎟️",
                "Coordinates ticket seat reservations, availability checks, and cancellations.",
                "/ui/bookings",
                buildBookingEndpoints()
        ));

        // 10. Payment Module
        modules.add(new ModuleInfo(
                "payment",
                "Payment Module",
                "💳",
                "Processes financial transactions, tracking payment records, and booking receipts.",
                "/ui/payments",
                buildPaymentEndpoints()
        ));

        // 11. Review Module
        modules.add(new ModuleInfo(
                "review",
                "Review Module",
                "⭐",
                "Collects post-travel passenger feedback, trip ratings, and moderation controls.",
                "/ui/reviews",
                buildReviewEndpoints()
        ));

        // 12. High-Impact Reports Module
        modules.add(new ModuleInfo(
                "reports",
                "High-Impact Reports Module",
                "📈",
                "Advanced analytical metrics, occupancy tracking, and revenue reporting across partner agencies.",
                "/ui/reports",
                buildReportsEndpoints()
        ));
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(
                "projectDescription",
                "A robust, transactional enterprise backend API ecosystem for state-wide passenger transport and scheduling systems, featuring relational validation, live concurrency checks, and secure high-impact financial reporting."
        );
        model.addAttribute("teamMembers", Arrays.asList(
                "Aayush Gautam",
                "Mehul Ashra",
                "Anirudh Bansal",
                "Archit Singh",
                "Deeksha S M"
        ));
        model.addAttribute("modules", modules);
        return "home";
    }

    @GetMapping("/modules/{id}")
    public String moduleDetail(@PathVariable String id, Model model) {
        ModuleInfo selectedModule = modules.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);

        if (selectedModule == null) {
            return "redirect:/";
        }

        model.addAttribute("module", selectedModule);
        return "module-detail";
    }

    private List<EndpointInfo> buildAddressEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/addresses", "Create a new address profile with coordinates and street details."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/addresses/{id}", "Fetch physical address profile details by unique identifier ID."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/addresses", "Retrieve lists of all active and registered address profiles."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/addresses/{id}", "Modify details, coordinates, and metadata of an existing address."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/addresses/{id}", "Remove an address profile from the system by ID."));
        return endpoints;
    }

    private List<EndpointInfo> buildAgencyEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/agencies", "Register a new transport agency in the enterprise system."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/agencies/{id}", "Get comprehensive agency profile details by unique ID."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/agencies", "Retrieve lists of all active and registered transport agencies."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/agencies/{id}", "Modify details, metadata, and corporate credentials of an agency."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/agencies/{id}", "Soft delete or deactivate a registered transport agency."));
        return endpoints;
    }

    private List<EndpointInfo> buildOfficeEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/agencies/{agencyId}/offices", "Add a new regional branch office under a parent agency."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/offices/{id}", "Fetch detailed office location, address references, and contact records."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/agencies/{agencyId}/offices", "List all active branch offices for a specific partner agency."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/offices/{id}", "Update office profiles, phone numbers, or email fields."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/offices/{id}", "Deactivate an agency office branch."));
        return endpoints;
    }

    private List<EndpointInfo> buildBusEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/buses", "Register a new transport vehicle asset in the system."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/buses", "Retrieve all registered buses in the global asset system."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/buses/{id}", "View full metadata, seating capacity, and status of a bus."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/buses/office/{officeId}", "Retrieve all registered buses assigned to an office branch."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/buses/{id}", "Update vehicle details, registration numbers, or bus status."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/buses/{id}", "Deactivate or retire a bus asset from service."));
        return endpoints;
    }

    private List<EndpointInfo> buildDriverEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/drivers", "Register a new commercial driver with license and contact info."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/drivers", "List all registered drivers across all transport agencies."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/drivers/{id}", "Retrieve driver profile details and commercial license data."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/drivers/{id}", "Terminate driver association or deactivate driver profile."));
        return endpoints;
    }

    private List<EndpointInfo> buildCustomerEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/customers", "Register a new customer profile in the system."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/customers", "Administrative list of all registered passengers."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}", "View passenger profile and account details."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/customers/{customerId}", "Modify passenger contact details, names, or addresses."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/customers/{customerId}", "Delete or deactivate passenger profile."));
        return endpoints;
    }

    private List<EndpointInfo> buildRouteEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/routes", "Establish a new transit route profile."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/routes", "Retrieve all configured system routes."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/routes/{id}", "Fetch detailed stopover mapping and durations by route ID."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/routes/{id}", "Update transit cities, distances, or duration hours."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/routes/{id}", "Disable and retire a route from active schedules."));
        return endpoints;
    }

    private List<EndpointInfo> buildTripEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/trips", "Create and schedule a new route trip."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/trips", "List all scheduled system trips."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/trips/{id}", "Retrieve details of a specific trip schedule."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/trips/search", "Query and search available trips by cities and date parameters."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/trips/{id}/seats", "Get count of available seat numbers on a trip."));
        endpoints.add(new EndpointInfo("PUT", "/api/v1/trips/{id}", "Edit scheduled trip details, pricing, or times."));
        endpoints.add(new EndpointInfo("PATCH", "/api/v1/trips/{id}/close", "Close trip booking lines (prevent new ticket purchases)."));
        return endpoints;
    }

    private List<EndpointInfo> buildBookingEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/trips/{tripId}/bookings", "Book a specific seat on an active scheduled trip."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/bookings", "View seat reservation history for a customer."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/bookings/{bookingId}", "Get full reservation ticket details by booking ID."));
        endpoints.add(new EndpointInfo("PATCH", "/api/v1/bookings/{bookingId}/cancel", "Cancel an active passenger booking."));
        return endpoints;
    }

    private List<EndpointInfo> buildPaymentEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/payments", "Process a new booking payment transaction."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/payments/{paymentId}", "Fetch payment receipt and transaction status."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/payments", "Retrieve full payment transaction history for a passenger."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/bookings/{bookingId}/payment", "Fetch the payment invoice linked to a booking."));
        endpoints.add(new EndpointInfo("PATCH", "/api/v1/payments/{paymentId}/status", "Administrative override of a payment transaction status."));
        return endpoints;
    }

    private List<EndpointInfo> buildReviewEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("POST", "/api/v1/trips/{tripId}/reviews", "Submit passenger rating and feedback for a completed trip."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/trips/{tripId}/reviews", "Fetch all passenger reviews for a trip."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/reviews", "Retrieve passenger review submissions list."));
        endpoints.add(new EndpointInfo("DELETE", "/api/v1/reviews/{reviewId}", "Moderate and remove a passenger review from the database."));
        return endpoints;
    }

    private List<EndpointInfo> buildReportsEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        endpoints.add(new EndpointInfo("GET", "/api/v1/reports/trips/occupancy", "Dynamic occupancy percentage mapping by date."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/reports/revenue/by-agency", "Multi-join partner agency financial revenue aggregates."));
        endpoints.add(new EndpointInfo("GET", "/api/v1/reports/customers/frequent", "High-value customer purchase and frequency tracking."));
        return endpoints;
    }

    public static class ModuleInfo {
        private final String id;
        private final String name;
        private final String icon;
        private final String description;
        private final String uiPath;
        private final List<EndpointInfo> endpoints;

        public ModuleInfo(String id, String name, String icon, String description, String uiPath, List<EndpointInfo> endpoints) {
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.description = description;
            this.uiPath = uiPath;
            this.endpoints = endpoints;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public String getDescription() {
            return description;
        }

        public String getUiPath() {
            return uiPath;
        }

        public List<EndpointInfo> getEndpoints() {
            return endpoints;
        }

        public int getEndpointCount() {
            return endpoints.size();
        }
    }

    public static class EndpointInfo {
        private final String method;
        private final String path;
        private final String description;

        public EndpointInfo(String method, String path, String description) {
            this.method = method;
            this.path = path;
            this.description = description;
        }

        public String getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public String getDescription() {
            return description;
        }
    }
}