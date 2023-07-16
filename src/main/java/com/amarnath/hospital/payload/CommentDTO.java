package com.amarnath.hospital.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    @NotEmpty
    @Size(min = 4, message = "Name should be at list 4 character long or above")
    private String name;

    @NotEmpty
    @Email(message = "Email is not valid please enter valid email id")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "You should type at list 10 words for comment")
    private String body;

}
