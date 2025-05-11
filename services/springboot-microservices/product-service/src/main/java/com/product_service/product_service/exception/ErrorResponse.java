package com.product_service.product_service.exception;

public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

    // Constructor, getters, setters
    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
