package com.busticket.busticketbooking.controller;

import com.busticket.busticketbooking.dto.ReviewDTO.ReviewRequestDTO;
import com.busticket.busticketbooking.dto.ReviewDTO.ReviewResponseDTO;
import com.busticket.busticketbooking.exception.ResourceNotFoundException;
import com.busticket.busticketbooking.service.ReviewService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/reviews")
public class ReviewUiController {

    private final ReviewService reviewService;

    public ReviewUiController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // LIST — paginated table of all reviews
    @GetMapping
    public String listReviews(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model
    ) {
        int requestedPage = Math.max(page, 1);
        int safePageIndex = requestedPage - 1;

        Page<ReviewResponseDTO> reviewPage = reviewService.getReviewPage(safePageIndex, size);

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("currentPage", requestedPage);
        model.addAttribute("pageSize", size);

        return "review/list";
    }

    // SHOW CREATE FORM — POST /api/v1/trips/{tripId}/reviews
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("review")) {
            model.addAttribute("review", new ReviewRequestDTO());
        }
        return "review/form";
    }

    // CREATE — POST /api/v1/trips/{tripId}/reviews
    // tripId is a separate path variable in the REST API, submitted as form param here
    @PostMapping
    public String createReview(
            @Valid @ModelAttribute("review") ReviewRequestDTO review,
            BindingResult bindingResult,
            @RequestParam(name = "tripId", required = false) Integer tripId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors() || tripId == null) {
            if (tripId == null) {
                model.addAttribute("tripIdError", "Trip ID is required");
            }
            model.addAttribute("tripId", tripId);
            return "review/form";
        }

        try {
            reviewService.submitReview(tripId, review);
            redirectAttributes.addFlashAttribute("successMessage", "Review submitted successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/reviews";
    }

    // DELETE — DELETE /api/v1/reviews/{reviewId}
    @DeleteMapping("/{id}")
    public String deleteReview(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            reviewService.removeReview(id);
            redirectAttributes.addFlashAttribute("successMessage", "Review removed successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/ui/reviews";
    }
}
