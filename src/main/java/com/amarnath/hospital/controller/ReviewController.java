package com.amarnath.hospital.controller;

import com.amarnath.hospital.payload.ReviewDTO;
import com.amarnath.hospital.payload.ReviewResponse;
import com.amarnath.hospital.service.ReviewService;
import com.amarnath.hospital.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {

        this.reviewService = reviewService;
    }

    @PostMapping("/api/v1/review")
    private ResponseEntity<ReviewDTO> createPost(@Valid @RequestBody ReviewDTO reviewDTO){

        return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.CREATED);

    }

    @GetMapping("/api/v1/review")
    private ReviewResponse getAllReviews(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.Default_Page_Number, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.Default_page_Size, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.Default_Sort_By, required = false) String sortBy,
            @RequestParam(value = "sortOrd", defaultValue = AppConstants.Default_Sort_Order, required = false) String sortOrd
            ){

        return reviewService.getAllReviews(pageNo, pageSize, sortBy, sortOrd);

    }

    @GetMapping("/api/v1/review/{reviewId}")
    private ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId){

        return ResponseEntity.ok(reviewService.getReviewById(reviewId));

    }

    @PutMapping("/api/v1/review/{reviewId}")
    private ResponseEntity<ReviewDTO> updateReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable Long reviewId){

        ReviewDTO reviewResponse = reviewService.updateReview(reviewDTO, reviewId);

        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);

    }

    @DeleteMapping("/api/v1/review/{reviewId}")
    private ResponseEntity<String> deleteReview(@PathVariable Long reviewId){

        reviewService.deleteReviewById(reviewId);

        return new ResponseEntity<>("Review Deleted Successfully!", HttpStatus.OK);

    }

}
