package com.amarnath.hospital.service;

import com.amarnath.hospital.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long reviewId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByReviewId(long reviewId);

    CommentDTO getCommentByIdForReview(long reviewId, long commentId);

    void deleteCommentByIdByReview(long reviewId, long commentId);

    CommentDTO updateCommentByIdByReview(long reviewId, long commentId, CommentDTO body);

}
