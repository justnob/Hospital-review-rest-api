package com.amarnath.hospital.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class ReviewDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title should be at list 2 character long or above")
    private String title;

    @NotEmpty
    @Size(min=5, message = "Description should not be null")
    private String description;

    @NotEmpty
    @Size(min = 3, message = "Content should not be null")
    private String content;

    @NotNull
    @Min(0)
    @Max(5)
    private int rating;

    private Set<CommentDTO> comments;


}
