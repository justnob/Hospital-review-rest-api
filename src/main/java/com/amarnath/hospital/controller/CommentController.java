package com.amarnath.hospital.controller;

import com.amarnath.hospital.payload.CommentDTO;
import com.amarnath.hospital.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/v1/review/{reviewId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long reviewId, @Valid @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.createComment(reviewId, commentDTO), HttpStatus.CREATED);

    }

    @GetMapping("/api/v1/review/{reviewId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentByReviewId(@PathVariable long reviewId){

        return new ResponseEntity<>(commentService.getAllCommentsByReviewId(reviewId), HttpStatus.OK);

    }

    @GetMapping("/api/v1/review/{reviewId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByIdByReview(@PathVariable long reviewId,
                                                             @PathVariable long commentId){
        CommentDTO commentByidForReview = commentService.getCommentByIdForReview(reviewId, commentId);

        return new ResponseEntity<>(commentByidForReview, HttpStatus.OK);

    }

    @DeleteMapping("/api/v1/review/{reviewId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByIdByReview(@PathVariable long reviewId,
                                                            @PathVariable long commentId){

        commentService.deleteCommentByIdByReview(reviewId, commentId);

        return new ResponseEntity<>("Comment Deleted Successfully!", HttpStatus.OK);

    }

    @PutMapping("/api/v1/review/{reviewId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentByIdByReview(
            @PathVariable long reviewId,
            @PathVariable long commentId,
            @Valid
            @RequestBody CommentDTO body
    ){

        CommentDTO commentDTO = commentService.updateCommentByIdByReview(reviewId, commentId, body);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);

    }

}
