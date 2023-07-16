package com.amarnath.hospital.service.impl;

import com.amarnath.hospital.entity.Comment;
import com.amarnath.hospital.entity.Review;
import com.amarnath.hospital.exception.ResourceNotFoundException;
import com.amarnath.hospital.exception.ReviewAPiException;
import com.amarnath.hospital.payload.CommentDTO;
import com.amarnath.hospital.repository.CommentRepository;
import com.amarnath.hospital.repository.ReviewRepository;
import com.amarnath.hospital.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private ReviewRepository reviewrepo;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(ReviewRepository reviewrepo, CommentRepository commentRepo, ModelMapper mapper) {
        this.reviewrepo = reviewrepo;
        this.commentRepo = commentRepo;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long reviewId, CommentDTO commentDTO) {

        //converting DTO to Entity
        Comment comment = mapToEntity(commentDTO);

        Review review = reviewrepo.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", reviewId)
        );

        //set comment to post entity
        comment.setReview(review);

        //save comment entity to the database
        Comment newComment = commentRepo.save(comment);

        CommentDTO commentResponse = mapToDTO(newComment);

        return commentResponse;

    }

    @Override
    public List<CommentDTO> getAllCommentsByReviewId(long reviewId) {

        List<Comment> allComments = commentRepo.findByReviewId(reviewId);

        List<CommentDTO> commentResponse = allComments.stream()
                .map(com -> mapToDTO(com))
                .collect(Collectors.toList());

        return commentResponse;

    }

    @Override
    public CommentDTO getCommentByIdForReview(long reviewId, long commentId) {

        Review review = reviewrepo.findById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("review", "id", reviewId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getReview().getId().equals(review.getId())){
            throw new ReviewAPiException(HttpStatus.BAD_REQUEST, "Comment Does Not Exist For This Review With This Id");
        }

        CommentDTO commentResponse = mapToDTO(comment);

        return commentResponse;

    }

    @Override
    public void deleteCommentByIdByReview(long reviewId, long commentId) {

        Review review = reviewrepo.findById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("review", "id", reviewId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getReview().getId().equals(review.getId())){
            throw new ReviewAPiException(HttpStatus.BAD_REQUEST, "Comment Does Not Exist For This Post With This Id");
        }

        commentRepo.delete(comment);

    }

    @Override
    public CommentDTO updateCommentByIdByReview(long reviewId, long commentId, CommentDTO body) {

        Review review = reviewrepo.findById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("review", "id", reviewId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getReview().getId().equals(review.getId())){
            throw new ReviewAPiException(HttpStatus.BAD_REQUEST, "Comment Does Not Exist For This Post With This Id");
        }

        comment.setName(body.getName());
        comment.setEmail(body.getEmail());
        comment.setBody(body.getBody());

        Comment updatedComment = commentRepo.save(comment);

        CommentDTO commentResponse = mapToDTO(updatedComment);

        return commentResponse;

    }


    private CommentDTO mapToDTO(Comment comment){

        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);

//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());

        return commentDTO;

    }

    private Comment mapToEntity(CommentDTO commentDTO){

        Comment comment = mapper.map(commentDTO, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());

        return comment;

    }

}
