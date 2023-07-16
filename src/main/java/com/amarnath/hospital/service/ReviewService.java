package com.amarnath.hospital.service;

import com.amarnath.hospital.entity.Review;
import com.amarnath.hospital.payload.ReviewDTO;
import com.amarnath.hospital.payload.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewResponse getAllReviews(int pageNo, int pageSize, String sortBy, String sortOrd);

    ReviewDTO getReviewById(Long reviewId);

    ReviewDTO updateReview(ReviewDTO reviewDTO, Long reviewId);

    void deleteReviewById(Long reviewId);

}
