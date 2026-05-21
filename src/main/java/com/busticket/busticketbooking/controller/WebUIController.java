package com.busticket.busticketbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebUIController {

    private final List<ModuleInfo> modules = new ArrayList<>();

    @PostConstruct
    public void init() {
        // 1. Address Module
        List<EndpointInfo> addressEndpoints = new ArrayList<>();
        addressEndpoints.add(new EndpointInfo("POST", "/api/v1/addresses", "Create a new address profile with coordinates and street details."));
        addressEndpoints.add(new EndpointInfo("GET", "/api/v1/addresses/{id}", "Fetch physical address profile details by unique identifier ID."));
        addressEndpoints.add(new EndpointInfo("GET", "/api/v1/addresses", "Retrieve lists of all active and registered address profiles."));
        addressEndpoints.add(new EndpointInfo("PUT", "/api/v1/addresses/{id}", "Modify details, coordinates, and metadata of an existing address."));
        addressEndpoints.add(new EndpointInfo("DELETE", "/api/v1/addresses/{id}", "Remove an address profile from the system by ID."));
        modules.add(new ModuleInfo("address", "Address Module", "📍", 
                "Manages physical address records, geographic coordinates, and regional location structures.", addressEndpoints));

        // 2. Agency Module
        List<EndpointInfo> agencyEndpoints = new ArrayList<>();
        agencyEndpoints.add(new EndpointInfo("POST", "/api/v1/agencies", "Register a new transport agency in the enterprise system."));
        agencyEndpoints.add(new EndpointInfo("GET", "/api/v1/agencies/{id}", "Get comprehensive agency profile details by unique ID."));
        agencyEndpoints.add(new EndpointInfo("GET", "/api/v1/agencies", "Retrieve lists of all active and registered transport agencies."));
        agencyEndpoints.add(new EndpointInfo("PUT", "/api/v1/agencies/{id}", "Modify details, metadata, and corporate credentials of an agency."));
        agencyEndpoints.add(new EndpointInfo("DELETE", "/api/v1/agencies/{id}", "Soft delete or deactivate a registered transport agency."));
        modules.add(new ModuleInfo("agency", "Agency Module", "🏢", 
                "Handles transport agency registrations, corporate structures, contact info, and partner statuses.", agencyEndpoints));

        // 3. Agency Office Module
        List<EndpointInfo> officeEndpoints = new ArrayList<>();
        officeEndpoints.add(new EndpointInfo("POST", "/api/v1/agencies/{agencyId}/offices", "Add a new regional branch office under a parent agency."));
        officeEndpoints.add(new EndpointInfo("GET", "/api/v1/offices/{id}", "Fetch detailed office location, address references, and contact records."));
        officeEndpoints.add(new EndpointInfo("GET", "/api/v1/agencies/{agencyId}/offices", "List all active branch offices for a specific partner agency."));
        officeEndpoints.add(new EndpointInfo("PUT", "/api/v1/offices/{id}", "Update office profiles, phone numbers, or email fields."));
        officeEndpoints.add(new EndpointInfo("DELETE", "/api/v1/offices/{id}", "Deactivate an agency office branch."));
        modules.add(new ModuleInfo("office", "Agency Office Module", "🏬", 
                "Manages regional branch offices under partner agencies, associating them with locations and addresses.", officeEndpoints));

        // 4. Bus Module
        List<EndpointInfo> busEndpoints = new ArrayList<>();
        busEndpoints.add(new EndpointInfo("POST", "/api/v1/buses", "Register a new transport vehicle asset in the system."));
        busEndpoints.add(new EndpointInfo("GET", "/api/v1/buses", "Retrieve all registered buses in the global asset system."));
        busEndpoints.add(new EndpointInfo("GET", "/api/v1/buses/{id}", "View full metadata, seating capacity, and status of a bus."));
        busEndpoints.add(new EndpointInfo("GET", "/api/v1/buses/office/{officeId}", "Retrieve all registered buses assigned to an office branch."));
        busEndpoints.add(new EndpointInfo("PUT", "/api/v1/buses/{id}", "Update vehicle details, registration numbers, or bus status."));
        busEndpoints.add(new EndpointInfo("DELETE", "/api/v1/buses/{id}", "Deactivate or retire a bus asset from service."));
        modules.add(new ModuleInfo("bus", "Bus Module", "🚌", 
                "Administers physical bus assets, matching them to offices, seating capacities, and custom transport types.", busEndpoints));

        // 5. Driver Module
        List<EndpointInfo> driverEndpoints = new ArrayList<>();
        driverEndpoints.add(new EndpointInfo("POST", "/api/v1/drivers", "Register a new commercial driver with license and contact info."));
        driverEndpoints.add(new EndpointInfo("GET", "/api/v1/drivers", "List all registered drivers across all transport agencies."));
        driverEndpoints.add(new EndpointInfo("GET", "/api/v1/drivers/{id}", "Retrieve driver profile details and commercial license data."));
        driverEndpoints.add(new EndpointInfo("DELETE", "/api/v1/drivers/{id}", "Terminate driver association or deactivate driver profile."));
        modules.add(new ModuleInfo("driver", "Driver Module", "👨‍✈️", 
                "Coordinates transport drivers, license status checks, office assignments, and active rosters.", driverEndpoints));

        // 6. Customer Module
        List<EndpointInfo> customerEndpoints = new ArrayList<>();
        customerEndpoints.add(new EndpointInfo("POST", "/api/v1/customers", "Register a new customer profile in the system."));
        customerEndpoints.add(new EndpointInfo("GET", "/api/v1/customers", "Administrative list of all registered passengers."));
        customerEndpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}", "View passenger profile and account details."));
        customerEndpoints.add(new EndpointInfo("PUT", "/api/v1/customers/{customerId}", "Modify passenger contact details, names, or addresses."));
        customerEndpoints.add(new EndpointInfo("DELETE", "/api/v1/customers/{customerId}", "Delete or deactivate passenger profile."));
        modules.add(new ModuleInfo("customer", "Customer Module", "👤", 
                "Manages passenger user profiles, contact registration, account statuses, and system roles.", customerEndpoints));

        // 7. Route Module
        List<EndpointInfo> routeEndpoints = new ArrayList<>();
        routeEndpoints.add(new EndpointInfo("POST", "/api/v1/routes", "Establish a new transit route profile."));
        routeEndpoints.add(new EndpointInfo("GET", "/api/v1/routes", "Retrieve all configured system routes."));
        routeEndpoints.add(new EndpointInfo("GET", "/api/v1/routes/{id}", "Fetch detailed stopover mapping and durations by route ID."));
        routeEndpoints.add(new EndpointInfo("PUT", "/api/v1/routes/{id}", "Update transit cities, distances, or duration hours."));
        routeEndpoints.add(new EndpointInfo("DELETE", "/api/v1/routes/{id}", "Disable and retire a route from active schedules."));
        modules.add(new ModuleInfo("route", "Route Module", "🗺️", 
                "Defines mapping of transit pathways between cities, travel durations, and stopover points.", routeEndpoints));

        // 8. Trip Module
        List<EndpointInfo> tripEndpoints = new ArrayList<>();
        tripEndpoints.add(new EndpointInfo("POST", "/api/v1/trips", "Create and schedule a new route trip."));
        tripEndpoints.add(new EndpointInfo("GET", "/api/v1/trips", "List all scheduled system trips."));
        tripEndpoints.add(new EndpointInfo("GET", "/api/v1/trips/{id}", "Retrieve details of a specific trip schedule."));
        tripEndpoints.add(new EndpointInfo("GET", "/api/v1/trips/search", "Query and search available trips by cities and date parameters."));
        tripEndpoints.add(new EndpointInfo("GET", "/api/v1/trips/{id}/seats", "Get count of available seat numbers on a trip."));
        tripEndpoints.add(new EndpointInfo("PUT", "/api/v1/trips/{id}", "Edit scheduled trip details, pricing, or times."));
        tripEndpoints.add(new EndpointInfo("PATCH", "/api/v1/trips/{id}/close", "Close trip booking lines (prevent new ticket purchases)."));
        modules.add(new ModuleInfo("trip", "Trip Module", "📅", 
                "Schedules transit trips for routes, managing active dates, fares, and passenger bookings.", tripEndpoints));

        // 9. Booking Module
        List<EndpointInfo> bookingEndpoints = new ArrayList<>();
        bookingEndpoints.add(new EndpointInfo("POST", "/api/v1/trips/{tripId}/bookings", "Book a specific seat on an active scheduled trip."));
        bookingEndpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/bookings", "View seat reservation history for a customer."));
        bookingEndpoints.add(new EndpointInfo("GET", "/api/v1/bookings/{bookingId}", "Get full reservation ticket details by booking ID."));
        bookingEndpoints.add(new EndpointInfo("PATCH", "/api/v1/bookings/{bookingId}/cancel", "Cancel an active passenger booking."));
        modules.add(new ModuleInfo("booking", "Booking Module", "🎟️", 
                "Coordinates ticket seat reservations, availability checks, and cancellations.", bookingEndpoints));

        // 10. Payment Module
        List<EndpointInfo> paymentEndpoints = new ArrayList<>();
        paymentEndpoints.add(new EndpointInfo("POST", "/api/v1/payments", "Process a new booking payment transaction."));
        paymentEndpoints.add(new EndpointInfo("GET", "/api/v1/payments/{paymentId}", "Fetch payment receipt and transaction status."));
        paymentEndpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/payments", "Retrieve full payment transaction history for a passenger."));
        paymentEndpoints.add(new EndpointInfo("GET", "/api/v1/bookings/{bookingId}/payment", "Fetch the payment invoice linked to a booking."));
        paymentEndpoints.add(new EndpointInfo("PATCH", "/api/v1/payments/{paymentId}/status", "Administrative override of a payment transaction status."));
        modules.add(new ModuleInfo("payment", "Payment Module", "💳", 
                "Processes financial transactions, tracking payment records, and booking receipts.", paymentEndpoints));

        // 11. Review Module
        List<EndpointInfo> reviewEndpoints = new ArrayList<>();
        reviewEndpoints.add(new EndpointInfo("POST", "/api/v1/trips/{tripId}/reviews", "Submit passenger rating and feedback for a completed trip."));
        reviewEndpoints.add(new EndpointInfo("GET", "/api/v1/trips/{tripId}/reviews", "Fetch all passenger reviews for a trip."));
        reviewEndpoints.add(new EndpointInfo("GET", "/api/v1/customers/{customerId}/reviews", "Retrieve passenger review submissions list."));
        reviewEndpoints.add(new EndpointInfo("DELETE", "/api/v1/reviews/{reviewId}", "Moderate and remove a passenger review from the database."));
        modules.add(new ModuleInfo("review", "Review Module", "⭐", 
                "Collects post-travel passenger feedback, trip ratings, and moderation controls.", reviewEndpoints));

        // 12. High-Impact Reports Module
        List<EndpointInfo> reportsEndpoints = new ArrayList<>();
        reportsEndpoints.add(new EndpointInfo("GET", "/api/v1/reports/trips/occupancy", "Dynamic occupancy percentage mapping by date."));
        reportsEndpoints.add(new EndpointInfo("GET", "/api/v1/reports/revenue/by-agency", "Multi-join partner agency financial revenue aggregates."));
        reportsEndpoints.add(new EndpointInfo("GET", "/api/v1/reports/customers/frequent", "High-value customer purchase and frequency tracking."));
        modules.add(new ModuleInfo("reports", "High-Impact Reports Module", "📈", 
                "Advanced analytical metrics, occupancy tracking, and revenue reporting across partner agencies.", reportsEndpoints));
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("projectDescription", "A robust, transactional enterprise backend API ecosystem for state-wide passenger transport and scheduling systems, featuring relational validation, live concurrency checks, and secure high-impact financial reporting.");
        model.addAttribute("teamMembers", java.util.Arrays.asList(
                "Mehul Ashra",
                "Aayush Gautam",
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

    // Static nested classes to represent data structures
    public static class ModuleInfo {
        private final String id;
        private final String name;
        private final String icon;
        private final String description;
        private final List<EndpointInfo> endpoints;

        public ModuleInfo(String id, String name, String icon, String description, List<EndpointInfo> endpoints) {
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.description = description;
            this.endpoints = endpoints;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getIcon() { return icon; }
        public String getDescription() { return description; }
        public List<EndpointInfo> getEndpoints() { return endpoints; }
        public int getEndpointCount() { return endpoints.size(); }
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

        public String getMethod() { return method; }
        public String getPath() { return path; }
        public String getDescription() { return description; }
    }
}
