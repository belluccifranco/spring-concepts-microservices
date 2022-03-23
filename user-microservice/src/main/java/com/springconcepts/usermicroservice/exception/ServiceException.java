package com.springconcepts.usermicroservice.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
