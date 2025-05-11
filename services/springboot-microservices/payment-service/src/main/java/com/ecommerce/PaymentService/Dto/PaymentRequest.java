package com.ecommerce.PaymentService.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for processing payment requests.
 * Contains information required to initiate a payment transaction.
 */
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates no-args constructor
@AllArgsConstructor // Generates all-args constructor
public class PaymentRequest {

    /**
     * The payment gateway to be used (e.g., Stripe, PayPal).
     * Must not be blank.
     */
    @NotBlank(message = "Payment gateway is required and cannot be blank.")
    private String gateway;

    /**
     * The amount to be paid.
     * Must be a positive value.
     */
    @NotNull(message = "Payment amount is required.")
    @Positive(message = "Payment amount must be greater than zero.")
    private BigDecimal amount;

    /**
     * The currency for the payment (e.g., USD, EUR).
     * Must not be blank and should be 3 characters long.
     */
    @NotBlank(message = "Currency is required and cannot be blank.")
    @Size(min = 3, max = 3, message = "Currency must be a 3-character code.")
    private String currency;

    /**
     * A description of the payment.
     * Must not be blank and should have a reasonable length.
     */
    @NotBlank(message = "Description is required and cannot be blank.")
    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;
}
