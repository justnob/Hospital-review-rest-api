package com.amarnath.hospital.exception;

import org.springframework.http.HttpStatus;

public class ReviewAPiException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public ReviewAPiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ReviewAPiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
