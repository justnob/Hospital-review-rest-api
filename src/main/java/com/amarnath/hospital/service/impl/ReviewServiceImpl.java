package com.amarnath.hospital.service.impl;

import com.amarnath.hospital.entity.Review;
import com.amarnath.hospital.exception.ResourceNotFoundException;
import com.amarnath.hospital.payload.ReviewDTO;
import com.amarnath.hospital.payload.ReviewResponse;
import com.amarnath.hospital.repository.ReviewRepository;
import com.amarnath.hospital.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {


    private ReviewRepository reviewRepo;
    private ModelMapper mapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepo, ModelMapper mapper) {

        this.reviewRepo = reviewRepo;
        this.mapper = mapper;
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {

        //converting DTO to entity
        Review review = mapToEntity(reviewDTO);


        Review newReview = reviewRepo.save(review);

        //converting entity to DTO
        ReviewDTO reviewResponse = mapToDTO(newReview);

        return reviewResponse;

    }

    @Override
    public ReviewResponse getAllReviews(int pageNo, int pageSize, String sortBy, String sortOrd) {

        Sort sort = sortOrd.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Review> reviews = reviewRepo.findAll(pageable);

        List<Review> listOfReviews = reviews.getContent();

        List<ReviewDTO> content = listOfReviews.stream()
                .map(review -> mapToDTO(review))
                .collect(Collectors.toList());

        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setContent(content);
        reviewResponse.setPageNo(reviews.getNumber());
        reviewResponse.setPageSize(reviews.getSize());
        reviewResponse.setTotalElements(reviews.getTotalElements());
        reviewResponse.setTotalPages(reviews.getTotalPages());
        reviewResponse.setLast(reviews.isLast());

        return reviewResponse;

    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {

        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("review", "id", reviewId));

        return mapToDTO(review);

    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, Long reviewId) {

        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("review", "id", reviewId));

        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepo.save(review);

        return mapToDTO(updatedReview);

    }

    @Override
    public void deleteReviewById(Long reviewId) {

        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("review", "id", reviewId));

        reviewRepo.delete(review);

    }

    //convert entity to DTO
    private ReviewDTO mapToDTO(Review review){

        ReviewDTO reviewDTO = mapper.map(review, ReviewDTO.class);

//        ReviewDTO reviewDTO = new ReviewDTO();
//        reviewDTO.setId(review.getId());
//        reviewDTO.setTitle(review.getTitle());
//        reviewDTO.setDescription(review.getDescription());
//        reviewDTO.setContent(review.getContent());
//        reviewDTO.setRating(review.getRating());

        return reviewDTO;
    }

    //convert DTO to Entity
    private Review mapToEntity(ReviewDTO reviewDTO){

        Review review = mapper.map(reviewDTO, Review.class);

//        Review review = new Review();
//        review.setTitle(reviewDTO.getTitle());
//        review.setDescription(reviewDTO.getDescription());
//        review.setContent(reviewDTO.getContent());
//        review.setRating(reviewDTO.getRating());

        return review;

    }

}
