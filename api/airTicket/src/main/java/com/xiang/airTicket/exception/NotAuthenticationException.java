package com.xiang.airTicket.exception;

public class NotAuthenticationException extends RuntimeException{
    public NotAuthenticationException(String message) {
        super(message);
    }
}
